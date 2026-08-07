package com.abdullahsolutions.mathurat.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.abdullahsolutions.mathurat.data.PrayerTimeRepository
import java.util.Calendar

/**
 * Schedules the prayer time reminders.
 *
 * Only the next few prayers are queued at a time ([MAX_ALARMS], roughly a day ahead). Each
 * alarm re-schedules the rest when it fires, and [reschedule] is also called on boot, on a
 * clock/timezone change, when the app is updated, and whenever the zone or settings change.
 * Everything is read from the on-disk cache, so this works with no network.
 */
object PrayerAlarmScheduler {

    const val EXTRA_PRAYER_KEY = "prayer_key"
    const val EXTRA_PRAYER_TIME = "prayer_time"
    const val EXTRA_ZONE = "zone"

    private const val MAX_ALARMS = 6
    private const val REQUEST_BASE = 41_000
    private const val LOOKAHEAD_DAYS = 3

    data class Slot(val key: String, val timeText: String, val triggerAt: Long)

    /** Cancels every queued reminder and re-queues the upcoming ones from cached times. */
    fun reschedule(context: Context) {
        val appContext = context.applicationContext
        cancelAll(appContext)
        if (!PrayerNotificationSettings.isEnabled(appContext)) return

        val zone = PrayerTimeRepository.savedZone(appContext) ?: return
        val data = PrayerTimeRepository.cached(appContext, zone) ?: return

        upcoming(appContext, data).forEachIndexed { index, slot ->
            schedule(appContext, index, slot, zone)
        }
    }

    fun cancelAll(context: Context) {
        val appContext = context.applicationContext
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (index in 0 until MAX_ALARMS) {
            // Extras are not part of PendingIntent matching, so a bare intent finds the
            // existing one. Null means nothing was queued for this slot.
            val existing = PendingIntent.getBroadcast(
                appContext,
                REQUEST_BASE + index,
                Intent(appContext, PrayerAlarmReceiver::class.java),
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            ) ?: continue
            alarmManager.cancel(existing)
            existing.cancel()
        }
    }

    /** The next [MAX_ALARMS] enabled prayers that are still in the future. */
    fun upcoming(context: Context, data: PrayerTimeRepository.YearData): List<Slot> =
        computeUpcoming(
            data,
            PrayerNotificationSettings.enabledPrayers(context),
            System.currentTimeMillis()
        )

    /**
     * Pure core of [upcoming], kept free of Context and the system clock so the day
     * rollover (after Isha the next slot is tomorrow's Fajr) can be tested.
     */
    internal fun computeUpcoming(
        data: PrayerTimeRepository.YearData,
        enabled: List<String>,
        nowMillis: Long
    ): List<Slot> {
        if (enabled.isEmpty()) return emptyList()

        val slots = mutableListOf<Slot>()
        val cursor = Calendar.getInstance(PrayerTimeRepository.malaysiaTz).apply {
            timeInMillis = nowMillis
        }

        for (offset in 0 until LOOKAHEAD_DAYS) {
            val day = PrayerTimeRepository.dayFor(data, cursor)
            if (day != null) {
                day.entries()
                    .filter { it.isPrayer && it.key in enabled }
                    .forEach { entry ->
                        val instant = PrayerTimeRepository.instantFor(day, entry.time)
                        if (instant != null && instant.timeInMillis > nowMillis) {
                            slots += Slot(entry.key, entry.time, instant.timeInMillis)
                        }
                    }
            }
            cursor.add(Calendar.DAY_OF_MONTH, 1)
            if (slots.size >= MAX_ALARMS) break
        }

        return slots.sortedBy { it.triggerAt }.take(MAX_ALARMS)
    }

    private fun schedule(context: Context, index: Int, slot: Slot, zone: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
            putExtra(EXTRA_PRAYER_KEY, slot.key)
            putExtra(EXTRA_PRAYER_TIME, slot.timeText)
            putExtra(EXTRA_ZONE, zone)
        }
        val pending = PendingIntent.getBroadcast(
            context,
            REQUEST_BASE + index,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Exact alarms need a user grant from Android 12 on. Without it the reminder still
        // fires, just not to the minute — better than nothing, and the screen says so.
        if (canScheduleExact(context)) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, slot.triggerAt, pending
            )
        } else {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, slot.triggerAt, pending
            )
        }
    }

    fun canScheduleExact(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val alarmManager = context.applicationContext
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return alarmManager.canScheduleExactAlarms()
    }
}
