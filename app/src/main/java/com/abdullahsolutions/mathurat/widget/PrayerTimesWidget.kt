package com.abdullahsolutions.mathurat.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.abdullahsolutions.mathurat.PrayerTimesActivity
import com.abdullahsolutions.mathurat.R
import com.abdullahsolutions.mathurat.data.PrayerTimeRepository
import com.abdullahsolutions.mathurat.data.PrayerZones
import com.abdullahsolutions.mathurat.model.PrayerDay
import com.abdullahsolutions.mathurat.notification.PrayerAlarmScheduler
import com.abdullahsolutions.mathurat.notification.PrayerNotificationSettings

/**
 * Home screen prayer times: the day's five prayers as a row of times, with the prayer we are
 * currently in highlighted.
 *
 * Everything is read from the cached year on disk, so the widget never touches the network.
 * Nothing on it ticks, so it needs a remote update only when the highlight should move to the
 * next cell — it schedules exactly one alarm at that moment rather than polling.
 */
class PrayerTimesWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { render(context, appWidgetManager, it) }
        scheduleNextRefresh(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_REFRESH) updateAll(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        // Last widget removed — drop the refresh alarm.
        alarmManager(context).cancel(refreshIntent(context))
    }

    private fun render(context: Context, manager: AppWidgetManager, widgetId: Int) {
        val english = context.getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE)
            .getBoolean("show_english", false)
        val views = RemoteViews(context.packageName, R.layout.widget_prayer_times)

        val zone = PrayerTimeRepository.savedZone(context)
        val data = zone?.let { PrayerTimeRepository.cached(context, it) }
        val today = data?.let {
            PrayerTimeRepository.dayFor(it, PrayerTimeRepository.malaysiaToday())
        }

        views.setOnClickPendingIntent(R.id.widgetRoot, openApp(context))

        if (today == null) {
            showMessage(
                views,
                if (english) {
                    "Open the app once to download prayer times."
                } else {
                    "Buka aplikasi sekali untuk memuat turun waktu solat."
                }
            )
            manager.updateAppWidget(widgetId, views)
            return
        }

        views.setViewVisibility(R.id.widgetMessage, View.GONE)
        views.setViewVisibility(R.id.widgetZone, View.VISIBLE)
        views.setViewVisibility(R.id.widgetTimesRow, View.VISIBLE)

        // Having times implies a zone was saved. Show the place, not the JAKIM code.
        views.setTextViewText(R.id.widgetZone, PrayerZones.displayNameFor(zone!!))

        renderDayTable(
            context,
            views,
            today,
            currentPrayerKey(today, System.currentTimeMillis())
        )
        manager.updateAppWidget(widgetId, views)
    }

    /**
     * The five obligatory prayers as a row of times, with [activeKey] pilled out. Names are
     * omitted deliberately — at 4x1 the times alone are legible, and the order is fixed.
     *
     * Every cell's background is set explicitly, including clearing it, so the pill cannot
     * smear across cells as the day advances.
     */
    private fun renderDayTable(
        context: Context,
        views: RemoteViews,
        today: PrayerDay,
        activeKey: String
    ) {
        val cells = intArrayOf(
            R.id.widgetTime0, R.id.widgetTime1, R.id.widgetTime2,
            R.id.widgetTime3, R.id.widgetTime4
        )
        val entries = today.entries().filter { it.isPrayer }

        entries.forEachIndexed { index, entry ->
            val cell = cells[index]
            views.setTextViewText(cell, entry.time)

            // Every cell is set explicitly, so no state carries over between renders.
            if (entry.key == activeKey) {
                views.setInt(cell, "setBackgroundResource", R.drawable.widget_time_active)
                views.setTextColor(cell, context.getColor(R.color.white))
            } else {
                views.setInt(cell, "setBackgroundResource", 0)
                views.setTextColor(cell, context.getColor(R.color.text_secondary))
            }
        }
    }

    private fun showMessage(views: RemoteViews, message: String) {
        views.setViewVisibility(R.id.widgetZone, View.GONE)
        views.setViewVisibility(R.id.widgetTimesRow, View.GONE)
        views.setViewVisibility(R.id.widgetMessage, View.VISIBLE)
        views.setTextViewText(R.id.widgetMessage, message)
    }

    private fun openApp(context: Context): PendingIntent {
        val intent = Intent(context, PrayerTimesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * One inexact alarm at the next prayer, to swap the widget over to the following one.
     * Inexact is fine here — the visible countdown is driven by the Chronometer, and a
     * minute of lag on the handover costs nothing.
     */
    private fun scheduleNextRefresh(context: Context) {
        val next = nextPrayer(context) ?: return
        alarmManager(context).set(
            AlarmManager.RTC,
            next.triggerAt + 1_000L,
            refreshIntent(context)
        )
    }

    private fun refreshIntent(context: Context): PendingIntent {
        val intent = Intent(context, PrayerTimesWidget::class.java).apply {
            action = ACTION_REFRESH
        }
        return PendingIntent.getBroadcast(
            context, REFRESH_REQUEST, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun alarmManager(context: Context) =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {
        private const val ACTION_REFRESH = "com.abdullahsolutions.mathurat.WIDGET_PRAYER_REFRESH"
        private const val REFRESH_REQUEST = 42_100

        /**
         * Next prayer regardless of which reminders the user enabled — the widget shows the
         * schedule, not the notification settings.
         */
        /**
         * The prayer whose time we are currently in — the last one that has already started.
         * Before Subuh that is still the previous night's Isyak, so the row never goes blank.
         *
         * Takes the clock as a parameter so the day boundaries can be tested.
         */
        internal fun currentPrayerKey(today: PrayerDay, nowMillis: Long): String {
            val started = today.entries()
                .filter { it.isPrayer }
                .lastOrNull { entry ->
                    val instant = PrayerTimeRepository.instantFor(today, entry.time)
                    instant != null && instant.timeInMillis <= nowMillis
                }
            return started?.key ?: "isha"
        }

        private fun nextPrayer(context: Context): PrayerAlarmScheduler.Slot? {
            val zone = PrayerTimeRepository.savedZone(context) ?: return null
            val data = PrayerTimeRepository.cached(context, zone) ?: return null
            return PrayerAlarmScheduler.computeUpcoming(
                data,
                PrayerNotificationSettings.prayerKeys,
                System.currentTimeMillis()
            ).firstOrNull()
        }

        /** Redraws every placed prayer widget — call after times or the zone change. */
        fun updateAll(context: Context) {
            val appContext = context.applicationContext
            val manager = AppWidgetManager.getInstance(appContext)
            val ids = manager.getAppWidgetIds(
                ComponentName(appContext, PrayerTimesWidget::class.java)
            )
            if (ids.isEmpty()) return
            val widget = PrayerTimesWidget()
            ids.forEach { widget.render(appContext, manager, it) }
            widget.scheduleNextRefresh(appContext)
        }
    }
}
