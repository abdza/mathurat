package com.abdullahsolutions.mathurat.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.abdullahsolutions.mathurat.model.PrayerDay
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.Executors
import java.util.zip.GZIPInputStream

/**
 * Prayer times from the JAKIM e-solat API
 * (`index.php?r=esolatApi/takwimsolat&period=year&zone=XXX00`).
 *
 * Deliberately light on their server:
 *  - fetches a WHOLE YEAR in one request (~77 KB, 365 rows) rather than polling daily
 *  - caches the raw JSON on disk, so a zone normally costs ONE request per year
 *  - serves cache instantly and only revalidates in the background after
 *    [REVALIDATE_AFTER_MS], which catches the rare mid-year correction
 *  - a failed request backs off for [RETRY_BACKOFF_MS] instead of retrying on every open
 *  - concurrent requests for the same zone/year collapse into one
 *
 * The API only ever serves the current calendar year, so the year cannot be requested
 * explicitly and next year's table can't be pre-fetched.
 */
object PrayerTimeRepository {

    private const val PREFS = "mathurat_prayer"
    const val KEY_ZONE = "zone"

    private const val BASE_URL =
        "https://www.e-solat.gov.my/index.php?r=esolatApi/takwimsolat&period=year&zone="
    private const val USER_AGENT = "Mathurat-Android"
    private const val CONNECT_TIMEOUT_MS = 15_000
    private const val READ_TIMEOUT_MS = 30_000
    private const val RETRY_BACKOFF_MS = 5 * 60 * 1000L
    private const val REVALIDATE_AFTER_MS = 90L * 24 * 60 * 60 * 1000

    /** JAKIM publishes in Malaysia time; all prayer instants are resolved in this zone. */
    val malaysiaTz: TimeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur")

    private val executor = Executors.newSingleThreadExecutor()

    // Lazy so that parsing stays usable (and unit-testable) without an Android looper.
    private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
    private val inFlight = mutableSetOf<String>()
    private val lastFailure = mutableMapOf<String, Long>()

    data class YearData(val zone: String, val year: Int, val days: List<PrayerDay>, val fetchedAt: Long)

    sealed class Outcome {
        /** Usable times. [refreshing] is true while a background revalidation is running. */
        data class Success(val data: YearData, val refreshing: Boolean) : Outcome()

        /** No usable times. [cached] holds older data if any survived. */
        data class Failure(val cached: YearData?, val reason: String) : Outcome()
    }

    fun savedZone(context: Context): String? = prefs(context).getString(KEY_ZONE, null)

    fun saveZone(context: Context, code: String) {
        prefs(context).edit().putString(KEY_ZONE, code).apply()
    }

    /** Today's date in Malaysia time — the calendar JAKIM's tables are keyed on. */
    fun malaysiaToday(): Calendar = Calendar.getInstance(malaysiaTz)

    /**
     * Cached times for [zone] read straight off disk, with no network and no main thread.
     * Used by the alarm scheduler, which must work from a broadcast receiver.
     */
    fun cached(context: Context, zone: String, year: Int = malaysiaToday().get(Calendar.YEAR)): YearData? =
        readCache(context, zone, year)

    /** Resolves "HH:mm" on [day] into an instant in Malaysia time. */
    fun instantFor(day: PrayerDay, time: String): Calendar? {
        val parts = time.split(":")
        if (parts.size < 2) return null
        val hour = parts[0].toIntOrNull() ?: return null
        val minute = parts[1].toIntOrNull() ?: return null
        return Calendar.getInstance(malaysiaTz).apply {
            set(Calendar.YEAR, day.year)
            set(Calendar.MONTH, day.month - 1)
            set(Calendar.DAY_OF_MONTH, day.day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    fun dayFor(data: YearData, calendar: Calendar): PrayerDay? {
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)
        return data.days.firstOrNull { it.year == year && it.month == month && it.day == day }
    }

    /**
     * Loads this year's table for [zone]. [onResult] is called on the main thread, and may be
     * called twice: once immediately with cached data, then again when a refresh completes.
     */
    fun load(
        context: Context,
        zone: String,
        forceRefresh: Boolean = false,
        onResult: (Outcome) -> Unit
    ) {
        val year = malaysiaToday().get(Calendar.YEAR)
        val key = "${zone}_$year"
        val cached = readCache(context, zone, year)

        val stale = cached == null ||
            System.currentTimeMillis() - cached.fetchedAt > REVALIDATE_AFTER_MS

        if (cached != null && !forceRefresh && !stale) {
            onResult(Outcome.Success(cached, refreshing = false))
            return
        }

        val now = System.currentTimeMillis()
        val backingOff = !forceRefresh &&
            lastFailure[key]?.let { now - it < RETRY_BACKOFF_MS } == true

        if (backingOff || !canStart(key)) {
            // Cached data is still good enough to show; just don't hit the server again yet.
            if (cached != null) {
                onResult(Outcome.Success(cached, refreshing = false))
            } else {
                onResult(Outcome.Failure(null, if (backingOff) "backoff" else "in_flight"))
            }
            return
        }

        // Show what we have while the network call runs.
        if (cached != null) onResult(Outcome.Success(cached, refreshing = true))

        executor.execute {
            val result = runCatching { fetchYear(zone) }
            mainHandler.post {
                inFlight.remove(key)
                result.fold(
                    onSuccess = { json ->
                        val days = parseDays(json)
                        if (days.isEmpty()) {
                            lastFailure[key] = System.currentTimeMillis()
                            onResult(Outcome.Failure(cached, "empty"))
                        } else {
                            lastFailure.remove(key)
                            val fetchedAt = System.currentTimeMillis()
                            writeCache(context, zone, year, json, fetchedAt)
                            pruneCache(context, zone, year)
                            onResult(
                                Outcome.Success(
                                    YearData(zone, year, days, fetchedAt),
                                    refreshing = false
                                )
                            )
                        }
                    },
                    onFailure = {
                        lastFailure[key] = System.currentTimeMillis()
                        onResult(Outcome.Failure(cached, it.message ?: "network"))
                    }
                )
            }
        }
    }

    private fun canStart(key: String): Boolean = inFlight.add(key)

    // ---------------------------------------------------------------- network

    private fun fetchYear(zone: String): String {
        val connection = (URL(BASE_URL + zone).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = CONNECT_TIMEOUT_MS
            readTimeout = READ_TIMEOUT_MS
            setRequestProperty("User-Agent", USER_AGENT)
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Accept-Encoding", "gzip")
        }
        try {
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IllegalStateException("HTTP ${connection.responseCode}")
            }
            val raw = connection.inputStream
            val stream = if (connection.contentEncoding?.contains("gzip", true) == true) {
                GZIPInputStream(raw)
            } else {
                raw
            }
            return stream.bufferedReader().use { it.readText() }
        } finally {
            connection.disconnect()
        }
    }

    // ----------------------------------------------------------------- parsing

    /** Malay month abbreviations used in the API's `date` field. */
    private val monthNames = mapOf(
        "jan" to 1, "feb" to 2, "mac" to 3, "apr" to 4, "mei" to 5, "jun" to 6,
        "jul" to 7, "ogos" to 8, "sep" to 9, "okt" to 10, "nov" to 11, "dis" to 12,
        // English fallbacks, in case the API is queried with a different lang setting.
        "mar" to 3, "may" to 5, "aug" to 8, "oct" to 10, "dec" to 12
    )

    internal fun parseDays(json: String): List<PrayerDay> {
        return try {
            val root = JSONObject(json)
            if (!root.optString("status").startsWith("OK")) return emptyList()
            // For an unknown zone the API returns an object here instead of an array.
            val array = root.optJSONArray("prayerTime") ?: return emptyList()
            (0 until array.length()).mapNotNull { i ->
                val o = array.optJSONObject(i) ?: return@mapNotNull null
                // e.g. "07-Ogos-2026"
                val parts = o.optString("date").split("-")
                if (parts.size != 3) return@mapNotNull null
                val day = parts[0].toIntOrNull() ?: return@mapNotNull null
                val month = monthNames[parts[1].lowercase(Locale.US)] ?: return@mapNotNull null
                val year = parts[2].toIntOrNull() ?: return@mapNotNull null
                PrayerDay(
                    year = year,
                    month = month,
                    day = day,
                    hijri = o.optString("hijri"),
                    date = o.optString("date"),
                    imsak = trimTime(o.optString("imsak")),
                    fajr = trimTime(o.optString("fajr")),
                    syuruk = trimTime(o.optString("syuruk")),
                    dhuha = trimTime(o.optString("dhuha")),
                    dhuhr = trimTime(o.optString("dhuhr")),
                    asr = trimTime(o.optString("asr")),
                    maghrib = trimTime(o.optString("maghrib")),
                    isha = trimTime(o.optString("isha"))
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /** "05:51:00" -> "05:51" */
    private fun trimTime(value: String): String =
        if (value.length >= 5) value.substring(0, 5) else value

    // ------------------------------------------------------------------ cache

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    // A year of times is ~77 KB — too big for SharedPreferences, so it lives in a file.
    private fun cacheFile(context: Context, zone: String, year: Int) =
        File(context.applicationContext.filesDir, "prayer_${zone}_$year.json")

    private fun readCache(context: Context, zone: String, year: Int): YearData? {
        val file = cacheFile(context, zone, year)
        if (!file.exists()) return null
        val days = runCatching { parseDays(file.readText()) }.getOrDefault(emptyList())
        if (days.isEmpty()) return null
        return YearData(zone, year, days, file.lastModified())
    }

    private fun writeCache(context: Context, zone: String, year: Int, json: String, fetchedAt: Long) {
        runCatching {
            val file = cacheFile(context, zone, year)
            file.writeText(json)
            file.setLastModified(fetchedAt)
        }
    }

    /** Drop tables for other zones and past years. */
    private fun pruneCache(context: Context, zone: String, year: Int) {
        val keep = cacheFile(context, zone, year).name
        context.applicationContext.filesDir.listFiles()
            ?.filter { it.name.startsWith("prayer_") && it.name != keep }
            ?.forEach { runCatching { it.delete() } }
    }
}
