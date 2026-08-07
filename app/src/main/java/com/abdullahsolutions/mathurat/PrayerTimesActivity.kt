package com.abdullahsolutions.mathurat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.abdullahsolutions.mathurat.data.PrayerTimeRepository
import com.abdullahsolutions.mathurat.data.PrayerZone
import com.abdullahsolutions.mathurat.data.PrayerZones
import com.abdullahsolutions.mathurat.databinding.ActivityPrayerTimesBinding
import com.abdullahsolutions.mathurat.databinding.ItemPrayerTimeBinding
import com.abdullahsolutions.mathurat.model.PrayerDay
import com.abdullahsolutions.mathurat.notification.PrayerAlarmScheduler
import com.abdullahsolutions.mathurat.notification.PrayerNotificationSettings
import com.abdullahsolutions.mathurat.notification.PrayerNotifier
import com.abdullahsolutions.mathurat.widget.PrayerTimesWidget
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Prayer times for the user's JAKIM zone, with a live countdown to the next prayer.
 * Data comes from [PrayerTimeRepository], which caches a full year per zone.
 */
class PrayerTimesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrayerTimesBinding

    private val settingsPrefs by lazy { getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE) }
    private val qiblaPrefs by lazy { getSharedPreferences("mathurat_qibla", Context.MODE_PRIVATE) }

    private var zone: PrayerZone = PrayerZones.byCode(PrayerZones.DEFAULT_ZONE)!!
    private var yearData: PrayerTimeRepository.YearData? = null
    private var today: PrayerDay? = null

    private val ticker = Handler(Looper.getMainLooper())
    private val tick = object : Runnable {
        override fun run() {
            updateCountdown()
            ticker.postDelayed(this, 1000L)
        }
    }

    private val en get() = settingsPrefs.getBoolean("show_english", false)

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            enableReminders()
        } else {
            // Without the permission the reminder would fire silently into nothing.
            PrayerNotificationSettings.setEnabled(this, false)
            setSwitchSilently(false)
        }
        renderNotificationState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrayerTimesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.title = if (en) "Prayer Times" else "Waktu Solat"
        binding.tvNextLabel.text = if (en) "Next prayer" else "Solat seterusnya"
        binding.tvZoneLabel.text = if (en) "Zone" else "Zon"
        binding.btnRefresh.text = if (en) "Refresh" else "Kemas Kini"

        zone = resolveZone()
        renderZone()

        applyBottomInset()

        binding.cardZone.setOnClickListener { showZonePicker() }
        binding.btnRefresh.setOnClickListener { load(forceRefresh = true) }

        setupNotifications()
        load()
    }

    override fun onResume() {
        super.onResume()
        ticker.post(tick)
        // The user may have changed notification or exact-alarm permissions in Settings.
        renderNotificationState()
    }

    override fun onPause() {
        super.onPause()
        ticker.removeCallbacks(tick)
    }

    /**
     * From Android 15 apps draw edge to edge, so the scroll content would otherwise end
     * underneath the navigation bar and hide the refresh button.
     */
    private fun applyBottomInset() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollContent) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = bars.bottom)
            insets
        }
    }

    /**
     * Saved zone wins; otherwise guess from the location the qibla screen cached, and fall
     * back to the default. The resolved zone is always persisted — the alarm scheduler runs
     * outside this activity and reads the zone from prefs, so leaving it unset would silently
     * stop reminders from ever being queued.
     */
    private fun resolveZone(): PrayerZone {
        PrayerTimeRepository.savedZone(this)?.let { saved ->
            PrayerZones.byCode(saved)?.let { return it }
        }
        val resolved = nearestZoneFromCachedLocation()
            ?: PrayerZones.byCode(PrayerZones.DEFAULT_ZONE)!!
        PrayerTimeRepository.saveZone(this, resolved.code)
        return resolved
    }

    private fun nearestZoneFromCachedLocation(): PrayerZone? {
        if (!qiblaPrefs.contains("lat")) return null
        val lat = qiblaPrefs.getFloat("lat", 0f).toDouble()
        val lon = qiblaPrefs.getFloat("lon", 0f).toDouble()
        return PrayerZones.nearest(lat, lon)
    }

    // ------------------------------------------------------------------- data

    private fun load(forceRefresh: Boolean = false) {
        binding.btnRefresh.isEnabled = false
        PrayerTimeRepository.load(this, zone.code, forceRefresh) { outcome ->
            when (outcome) {
                is PrayerTimeRepository.Outcome.Success -> {
                    yearData = outcome.data
                    binding.btnRefresh.isEnabled = !outcome.refreshing
                    render(outcome.data, refreshing = outcome.refreshing)
                }
                is PrayerTimeRepository.Outcome.Failure -> {
                    binding.btnRefresh.isEnabled = true
                    val cached = outcome.cached
                    if (cached != null) {
                        yearData = cached
                        render(cached, refreshing = false, failed = true)
                    } else {
                        showEmpty(outcome.reason)
                    }
                }
            }
        }
    }

    // --------------------------------------------------------------------- UI

    private fun renderZone() {
        // Lead with the place; keep the JAKIM code and the district list underneath so the
        // user can still confirm their area is the right one.
        binding.tvZoneCode.text = zone.displayName
        binding.tvZoneAreas.text = "${zone.code} · ${zone.areas}"
    }

    private fun render(
        data: PrayerTimeRepository.YearData,
        refreshing: Boolean,
        failed: Boolean = false
    ) {
        val now = PrayerTimeRepository.malaysiaToday()
        val day = PrayerTimeRepository.dayFor(data, now)
        today = day

        if (day == null) {
            showEmpty("no_day")
            return
        }

        binding.tvDate.text = formatDate(now, day)
        renderRows(day)
        updateCountdown()
        binding.tvStatus.text = statusText(data, refreshing, failed)

        // Alarms are built from the cache, so (re)queue them once times are available —
        // this also covers a zone change and the roll into a newly fetched year.
        if (PrayerNotificationSettings.isEnabled(this)) {
            PrayerAlarmScheduler.reschedule(this)
        }
        PrayerTimesWidget.updateAll(this)
    }

    private fun renderRows(day: PrayerDay) {
        val container = binding.llTimes
        container.removeAllViews()
        val inflater = LayoutInflater.from(this)
        val nextKey = nextPrayer()?.first

        day.entries().forEachIndexed { index, entry ->
            val row = ItemPrayerTimeBinding.inflate(inflater, container, false)
            row.tvName.text = labelFor(entry.key)
            row.tvTime.text = entry.time

            val isNext = entry.key == nextKey
            row.vAccent.visibility = if (isNext) View.VISIBLE else View.INVISIBLE
            val color = when {
                isNext -> ContextCompat.getColor(this, R.color.colorPrimary)
                entry.isPrayer -> ContextCompat.getColor(this, R.color.text_primary)
                else -> ContextCompat.getColor(this, R.color.text_secondary)
            }
            row.tvName.setTextColor(color)
            row.tvTime.setTextColor(color)
            row.tvName.typeface = if (isNext) android.graphics.Typeface.DEFAULT_BOLD else null
            row.tvTime.typeface = if (isNext) android.graphics.Typeface.DEFAULT_BOLD else null
            if (isNext) row.rowRoot.setBackgroundResource(R.drawable.bg_counter_active)

            container.addView(row.root)

            if (index != day.entries().lastIndex) {
                val divider = View(this).apply {
                    layoutParams = android.widget.LinearLayout.LayoutParams(
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1
                    )
                    setBackgroundColor(ContextCompat.getColor(this@PrayerTimesActivity, R.color.divider))
                }
                container.addView(divider)
            }
        }
    }

    /**
     * The next obligatory prayer as (key, instant). After Isha this rolls over to
     * tomorrow's Fajr.
     */
    private fun nextPrayer(): Pair<String, Calendar>? {
        val day = today ?: return null
        val now = System.currentTimeMillis()

        day.entries().filter { it.isPrayer }.forEach { entry ->
            val instant = PrayerTimeRepository.instantFor(day, entry.time) ?: return@forEach
            if (instant.timeInMillis > now) return entry.key to instant
        }

        // Past Isha — use tomorrow's Fajr.
        val data = yearData ?: return null
        val tomorrow = PrayerTimeRepository.malaysiaToday().apply { add(Calendar.DAY_OF_MONTH, 1) }
        val nextDay = PrayerTimeRepository.dayFor(data, tomorrow) ?: return null
        val instant = PrayerTimeRepository.instantFor(nextDay, nextDay.fajr) ?: return null
        return "fajr" to instant
    }

    private fun updateCountdown() {
        val next = nextPrayer()
        if (next == null) {
            binding.tvNextName.text = "—"
            binding.tvNextTime.text = "—"
            binding.tvCountdown.text = ""
            return
        }

        val (key, instant) = next
        binding.tvNextName.text = labelFor(key)
        binding.tvNextTime.text = String.format(
            Locale.US, "%02d:%02d",
            instant.get(Calendar.HOUR_OF_DAY), instant.get(Calendar.MINUTE)
        )

        var remaining = (instant.timeInMillis - System.currentTimeMillis()) / 1000
        if (remaining < 0) {
            // A prayer time just passed — rebuild so the highlight moves on.
            renderRows(today ?: return)
            remaining = 0
        }
        val hours = remaining / 3600
        val minutes = (remaining % 3600) / 60
        val seconds = remaining % 60
        val clock = String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
        binding.tvCountdown.text = if (en) "in $clock" else "dalam $clock"
    }

    private fun formatDate(now: Calendar, day: PrayerDay): String {
        val pattern = if (en) "EEEE, d MMMM yyyy" else "EEEE, d MMMM yyyy"
        val locale = if (en) Locale.ENGLISH else MALAY_LOCALE
        val formatter = SimpleDateFormat(pattern, locale).apply {
            timeZone = PrayerTimeRepository.malaysiaTz
        }
        val gregorian = formatter.format(Date(now.timeInMillis))
        return if (day.hijri.isNotEmpty()) "$gregorian  ·  ${day.hijri} H" else gregorian
    }

    private fun statusText(
        data: PrayerTimeRepository.YearData,
        refreshing: Boolean,
        failed: Boolean
    ): String {
        val source = if (en) "Source: JAKIM e-Solat" else "Sumber: JAKIM e-Solat"
        return when {
            refreshing -> if (en) "$source · updating…" else "$source · mengemas kini…"
            failed -> if (en) {
                "$source · offline, showing saved times"
            } else {
                "$source · luar talian, memaparkan waktu tersimpan"
            }
            else -> {
                val locale = if (en) Locale.ENGLISH else MALAY_LOCALE
                val formatter = SimpleDateFormat("d MMM yyyy", locale).apply {
                    timeZone = PrayerTimeRepository.malaysiaTz
                }
                val when_ = formatter.format(Date(data.fetchedAt))
                if (en) {
                    "$source · ${data.year} times saved on $when_"
                } else {
                    "$source · waktu ${data.year} disimpan pada $when_"
                }
            }
        }
    }

    private fun showEmpty(reason: String) {
        binding.llTimes.removeAllViews()
        binding.tvNextName.text = "—"
        binding.tvNextTime.text = "—"
        binding.tvCountdown.text = ""
        binding.tvDate.text = ""
        binding.tvStatus.text = if (en) {
            "Could not load prayer times. Check your connection and tap Refresh."
        } else {
            "Tidak dapat memuatkan waktu solat. Semak sambungan dan tekan Kemas Kini."
        }
    }

    // --------------------------------------------------------- notifications

    private fun setupNotifications() {
        binding.tvNotifyTitle.text =
            if (en) "Prayer time reminders" else "Peringatan waktu solat"

        setSwitchSilently(PrayerNotificationSettings.isEnabled(this))
        binding.tvNotifyPrayers.setOnClickListener { showPrayerPicker() }
        renderNotificationState()
    }

    /** Turning reminders on needs POST_NOTIFICATIONS from Android 13. */
    private fun requestReminders() {
        val needsPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED

        if (needsPermission) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            enableReminders()
            renderNotificationState()
        }
    }

    private fun enableReminders() {
        PrayerNotificationSettings.setEnabled(this, true)
        setSwitchSilently(true)
        PrayerNotifier.ensureChannel(this)
        PrayerAlarmScheduler.reschedule(this)
    }

    /** Updates the switch without re-entering the enable/disable handlers. */
    private fun setSwitchSilently(checked: Boolean) {
        binding.switchNotify.setOnCheckedChangeListener(null)
        binding.switchNotify.isChecked = checked
        binding.switchNotify.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) requestReminders() else disableReminders()
        }
    }

    private fun disableReminders() {
        PrayerNotificationSettings.setEnabled(this, false)
        PrayerAlarmScheduler.cancelAll(this)
        renderNotificationState()
    }

    private fun renderNotificationState() {
        val enabled = PrayerNotificationSettings.isEnabled(this)
        val selected = PrayerNotificationSettings.enabledPrayers(this)

        binding.tvNotifyPrayers.visibility = if (enabled) View.VISIBLE else View.GONE
        binding.tvNotifyPrayers.text = when {
            selected.isEmpty() -> if (en) "No prayers selected" else "Tiada solat dipilih"
            selected.size == PrayerNotificationSettings.prayerKeys.size ->
                if (en) "All five prayers" else "Kelima-lima waktu solat"
            else -> selected.joinToString(", ") { labelFor(it) }
        }

        val systemBlocked = !NotificationManagerCompat.from(this).areNotificationsEnabled()
        val inexact = !PrayerAlarmScheduler.canScheduleExact(this)

        when {
            !enabled -> hideNotificationWarning()

            systemBlocked -> showNotificationWarning(
                message = if (en) {
                    "Notifications are turned off for this app, so reminders will not appear."
                } else {
                    "Pemberitahuan dimatikan untuk aplikasi ini, jadi peringatan tidak akan dipaparkan."
                },
                action = if (en) "Open notification settings" else "Buka tetapan pemberitahuan"
            ) { openAppNotificationSettings() }

            inexact -> showNotificationWarning(
                message = if (en) {
                    "Exact alarms are not allowed, so reminders may arrive a few minutes late."
                } else {
                    "Penggera tepat tidak dibenarkan, jadi peringatan mungkin lewat beberapa minit."
                },
                action = if (en) "Allow exact alarms" else "Benarkan penggera tepat"
            ) { openExactAlarmSettings() }

            selected.isEmpty() -> showNotificationWarning(
                message = if (en) {
                    "Pick at least one prayer to be reminded about."
                } else {
                    "Pilih sekurang-kurangnya satu waktu solat untuk diperingatkan."
                },
                action = null
            ) {}

            else -> hideNotificationWarning()
        }
    }

    private fun showNotificationWarning(message: String, action: String?, onAction: () -> Unit) {
        binding.tvNotifyWarning.visibility = View.VISIBLE
        binding.tvNotifyWarning.text = message
        if (action == null) {
            binding.btnNotifyFix.visibility = View.GONE
        } else {
            binding.btnNotifyFix.visibility = View.VISIBLE
            binding.btnNotifyFix.text = action
            binding.btnNotifyFix.setOnClickListener { onAction() }
        }
    }

    private fun hideNotificationWarning() {
        binding.tvNotifyWarning.visibility = View.GONE
        binding.btnNotifyFix.visibility = View.GONE
    }

    private fun openAppNotificationSettings() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        } else {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package", packageName, null))
        }
        runCatching { startActivity(intent) }
    }

    private fun openExactAlarmSettings() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            .setData(Uri.fromParts("package", packageName, null))
        runCatching { startActivity(intent) }
    }

    private fun showPrayerPicker() {
        val keys = PrayerNotificationSettings.prayerKeys
        val labels = keys.map { labelFor(it) }.toTypedArray()
        val checked = keys.map { PrayerNotificationSettings.isPrayerEnabled(this, it) }
            .toBooleanArray()

        AlertDialog.Builder(this)
            .setTitle(if (en) "Remind me for" else "Ingatkan saya untuk")
            .setMultiChoiceItems(labels, checked) { _, which, isChecked ->
                checked[which] = isChecked
            }
            .setPositiveButton(if (en) "Save" else "Simpan") { _, _ ->
                keys.forEachIndexed { index, key ->
                    PrayerNotificationSettings.setPrayerEnabled(this, key, checked[index])
                }
                PrayerAlarmScheduler.reschedule(this)
                renderNotificationState()
            }
            .setNegativeButton(if (en) "Cancel" else "Batal", null)
            .show()
    }

    // ---------------------------------------------------------------- picker

    private fun showZonePicker() {
        val zones = PrayerZones.all
        val labels = zones
            .map { "${it.displayName} — ${it.state}\n${it.code} · ${it.areas}" }
            .toTypedArray()
        val checked = zones.indexOfFirst { it.code == zone.code }

        AlertDialog.Builder(this)
            .setTitle(if (en) "Select zone" else "Pilih zon")
            .setSingleChoiceItems(labels, checked) { dialog, which ->
                dialog.dismiss()
                selectZone(zones[which])
            }
            .setNeutralButton(if (en) "Use my location" else "Guna lokasi saya") { _, _ ->
                val guess = nearestZoneFromCachedLocation()
                if (guess == null) {
                    binding.tvStatus.text = if (en) {
                        "No saved location yet — open Qibla Direction once to detect your area."
                    } else {
                        "Tiada lokasi tersimpan — buka Arah Kiblat sekali untuk mengesan kawasan anda."
                    }
                } else {
                    selectZone(guess)
                }
            }
            .setNegativeButton(if (en) "Cancel" else "Batal", null)
            .show()
    }

    private fun selectZone(newZone: PrayerZone) {
        if (newZone.code == zone.code) return
        zone = newZone
        PrayerTimeRepository.saveZone(this, newZone.code)
        renderZone()
        yearData = null
        today = null
        load()
    }

    private fun labelFor(key: String): String = when (key) {
        "imsak" -> "Imsak"
        "fajr" -> if (en) "Fajr" else "Subuh"
        "syuruk" -> if (en) "Sunrise" else "Syuruk"
        "dhuha" -> "Dhuha"
        "dhuhr" -> if (en) "Dhuhr" else "Zohor"
        "asr" -> if (en) "Asr" else "Asar"
        "maghrib" -> "Maghrib"
        "isha" -> if (en) "Isha" else "Isyak"
        else -> key
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val MALAY_LOCALE: Locale = Locale.forLanguageTag("ms-MY")
    }
}
