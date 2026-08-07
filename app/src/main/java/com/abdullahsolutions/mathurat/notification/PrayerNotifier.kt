package com.abdullahsolutions.mathurat.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abdullahsolutions.mathurat.PrayerTimesActivity
import com.abdullahsolutions.mathurat.R

/**
 * Builds and posts the "waktu solat has entered" notification.
 *
 * The channel is created with the default notification sound. To hear an actual azan the user
 * can point the channel at their own audio file from Android's per-channel notification
 * settings — the app ships no azan recording of its own.
 */
object PrayerNotifier {

    const val CHANNEL_ID = "prayer_times"
    private const val NOTIFICATION_ID_BASE = 51_000

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        if (manager.getNotificationChannel(CHANNEL_ID) != null) return

        val english = isEnglish(context)
        val channel = NotificationChannel(
            CHANNEL_ID,
            if (english) "Prayer times" else "Waktu solat",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = if (english) {
                "Announces when each prayer time enters"
            } else {
                "Memberitahu apabila masuk waktu solat"
            }
            enableVibration(true)
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                    .build()
            )
        }
        manager.createNotificationChannel(channel)
    }

    fun notifyPrayer(context: Context, prayerKey: String, time: String, zone: String) {
        ensureChannel(context)
        val english = isEnglish(context)
        val name = labelFor(prayerKey, english)

        val tapIntent = Intent(context, PrayerTimesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pending = PendingIntent.getActivity(
            context,
            0,
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = if (english) "$name — $time" else "Waktu $name — $time"
        val body = if (english) {
            "It is now time for $name ($zone)"
        } else {
            "Telah masuk waktu $name bagi zon $zone"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_prayer)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(pending)
            .build()

        val id = NOTIFICATION_ID_BASE + PrayerNotificationSettings.prayerKeys.indexOf(prayerKey)
        try {
            NotificationManagerCompat.from(context).notify(id, notification)
        } catch (e: SecurityException) {
            // POST_NOTIFICATIONS was revoked between scheduling and firing.
        }
    }

    private fun isEnglish(context: Context): Boolean =
        context.getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE)
            .getBoolean("show_english", false)

    fun labelFor(key: String, english: Boolean): String = when (key) {
        "fajr" -> if (english) "Fajr" else "Subuh"
        "dhuhr" -> if (english) "Dhuhr" else "Zohor"
        "asr" -> if (english) "Asr" else "Asar"
        "maghrib" -> "Maghrib"
        "isha" -> if (english) "Isha" else "Isyak"
        else -> key
    }
}
