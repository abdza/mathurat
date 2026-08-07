package com.abdullahsolutions.mathurat.notification

import android.content.Context

/** User preferences for prayer time reminders, stored alongside the selected zone. */
object PrayerNotificationSettings {

    private const val PREFS = "mathurat_prayer"
    private const val KEY_ENABLED = "notify_enabled"
    private const val KEY_PREFIX = "notify_"

    /** The five obligatory prayers, in daily order — the only ones that can be announced. */
    val prayerKeys = listOf("fajr", "dhuhr", "asr", "maghrib", "isha")

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun isEnabled(context: Context): Boolean =
        prefs(context).getBoolean(KEY_ENABLED, false)

    fun setEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_ENABLED, enabled).apply()
    }

    fun isPrayerEnabled(context: Context, key: String): Boolean =
        prefs(context).getBoolean(KEY_PREFIX + key, true)

    fun setPrayerEnabled(context: Context, key: String, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_PREFIX + key, enabled).apply()
    }

    fun enabledPrayers(context: Context): List<String> =
        prayerKeys.filter { isPrayerEnabled(context, it) }
}
