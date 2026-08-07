package com.abdullahsolutions.mathurat.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.RemoteViews
import com.abdullahsolutions.mathurat.R

/**
 * Home screen zikir counter: a large tap target that counts, and a small reset beside it.
 *
 * The count lives in the same `mathurat_zikr_counter` prefs the in-app counter uses, so the
 * widget and [com.abdullahsolutions.mathurat.ZikrCounterActivity] always show the same number.
 */
class ZikrCounterWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { render(context, appWidgetManager, it) }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.action) {
            ACTION_INCREMENT -> {
                val prefs = countPrefs(context)
                val count = prefs.getInt(KEY_COUNT, 0) + 1
                prefs.edit().putInt(KEY_COUNT, count).apply()
                vibrate(context, count)
                updateAll(context)
            }
            ACTION_RESET -> {
                countPrefs(context).edit().putInt(KEY_COUNT, 0).apply()
                updateAll(context)
            }
        }
    }

    /** Mirrors the in-app counter's milestone feedback at 33 and 100. */
    private fun vibrate(context: Context, count: Int) {
        val settings = context.getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE)
        val pattern = when {
            count % 100 == 0 && settings.getBoolean("vibrate_on_100", true) ->
                longArrayOf(0, 400, 150, 400)
            count % 33 == 0 && settings.getBoolean("vibrate_on_33", true) ->
                longArrayOf(0, 400)
            settings.getBoolean("vibrate_on_click", false) -> longArrayOf(0, 30)
            else -> return
        }

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager)
                .defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (!vibrator.hasVibrator()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val amplitudes = IntArray(pattern.size) { i -> if (i % 2 == 0) 0 else 255 }
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }

    private fun render(context: Context, manager: AppWidgetManager, widgetId: Int) {
        val english = context.getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE)
            .getBoolean("show_english", false)
        val count = countPrefs(context).getInt(KEY_COUNT, 0)

        val views = RemoteViews(context.packageName, R.layout.widget_zikr_counter).apply {
            setTextViewText(R.id.widgetCount, count.toString())
            setTextViewText(
                R.id.widgetHint,
                if (english) "Tap to count" else "Ketuk untuk mengira"
            )
            setTextViewText(
                R.id.widgetResetLabel,
                if (english) "Reset" else "Set Semula"
            )
            setOnClickPendingIntent(
                R.id.widgetTapZone,
                broadcast(context, ACTION_INCREMENT, widgetId)
            )
            setOnClickPendingIntent(
                R.id.widgetReset,
                broadcast(context, ACTION_RESET, widgetId)
            )
        }

        manager.updateAppWidget(widgetId, views)
    }

    private fun broadcast(context: Context, action: String, widgetId: Int): PendingIntent {
        val intent = Intent(context, ZikrCounterWidget::class.java).apply {
            this.action = action
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        }
        // Request code must differ per action and per widget, or the PendingIntents collide.
        val requestCode = widgetId * 10 + if (action == ACTION_INCREMENT) 1 else 2
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private const val ACTION_INCREMENT = "com.abdullahsolutions.mathurat.WIDGET_ZIKR_INCREMENT"
        private const val ACTION_RESET = "com.abdullahsolutions.mathurat.WIDGET_ZIKR_RESET"
        private const val KEY_COUNT = "count"

        private fun countPrefs(context: Context) =
            context.applicationContext
                .getSharedPreferences("mathurat_zikr_counter", Context.MODE_PRIVATE)

        /** Redraws every placed counter widget — call after the in-app counter changes. */
        fun updateAll(context: Context) {
            val appContext = context.applicationContext
            val manager = AppWidgetManager.getInstance(appContext)
            val ids = manager.getAppWidgetIds(
                ComponentName(appContext, ZikrCounterWidget::class.java)
            )
            if (ids.isEmpty()) return
            val widget = ZikrCounterWidget()
            ids.forEach { widget.render(appContext, manager, it) }
        }
    }
}
