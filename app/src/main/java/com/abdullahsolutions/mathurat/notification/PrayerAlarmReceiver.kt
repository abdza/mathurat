package com.abdullahsolutions.mathurat.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abdullahsolutions.mathurat.widget.PrayerTimesWidget

/** Fires at a prayer time: posts the notification, then queues the following prayers. */
class PrayerAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val key = intent.getStringExtra(PrayerAlarmScheduler.EXTRA_PRAYER_KEY)
        val time = intent.getStringExtra(PrayerAlarmScheduler.EXTRA_PRAYER_TIME).orEmpty()
        val zone = intent.getStringExtra(PrayerAlarmScheduler.EXTRA_ZONE).orEmpty()

        if (key != null &&
            PrayerNotificationSettings.isEnabled(context) &&
            PrayerNotificationSettings.isPrayerEnabled(context, key)
        ) {
            PrayerNotifier.notifyPrayer(context, key, time, zone)
        }

        // Keep the queue topped up as each alarm is consumed.
        PrayerAlarmScheduler.reschedule(context)
        PrayerTimesWidget.updateAll(context)
    }
}
