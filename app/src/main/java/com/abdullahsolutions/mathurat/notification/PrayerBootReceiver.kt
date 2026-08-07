package com.abdullahsolutions.mathurat.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abdullahsolutions.mathurat.widget.PrayerTimesWidget

/**
 * Alarms do not survive a reboot, an app update, or a clock change, so re-queue them
 * whenever the system tells us one of those happened.
 */
class PrayerBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_TIMEZONE_CHANGED,
            "android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED" -> {
                PrayerAlarmScheduler.reschedule(context)
                // The widget's own refresh alarm is lost too.
                PrayerTimesWidget.updateAll(context)
            }
        }
    }
}
