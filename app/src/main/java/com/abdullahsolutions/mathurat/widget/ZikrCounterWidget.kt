package com.abdullahsolutions.mathurat.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.VibrationAttributes
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
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
                playClick()
                vibrate(context, count)
                updateAll(context)
            }
            ACTION_RESET -> {
                countPrefs(context).edit().putInt(KEY_COUNT, 0).apply()
                updateAll(context)
            }
        }
    }

    /** Same beep as the in-app counter. Released on a delay so the 40ms tone finishes. */
    private fun playClick() {
        try {
            val tone = ToneGenerator(AudioManager.STREAM_MUSIC, 60)
            tone.startTone(ToneGenerator.TONE_PROP_BEEP, 40)
            Handler(Looper.getMainLooper()).postDelayed({ tone.release() }, 200)
        } catch (e: Exception) {
            // ignore if tone not available
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

        // This runs in a broadcast receiver, i.e. a background process. From Android 12/13
        // the system silently drops background vibrations unless they carry an "alerting"
        // usage (alarm/notification), so a plain vibrate() here never reaches the motor.
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                val amplitudes = IntArray(pattern.size) { i -> if (i % 2 == 0) 0 else 255 }
                vibrator.vibrate(
                    VibrationEffect.createWaveform(pattern, amplitudes, -1),
                    VibrationAttributes.createForUsage(VibrationAttributes.USAGE_ALARM)
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val amplitudes = IntArray(pattern.size) { i -> if (i % 2 == 0) 0 else 255 }
                @Suppress("DEPRECATION")
                vibrator.vibrate(
                    VibrationEffect.createWaveform(pattern, amplitudes, -1),
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                )
            }
            else -> {
                @Suppress("DEPRECATION")
                vibrator.vibrate(pattern, -1)
            }
        }
    }

    private fun render(context: Context, manager: AppWidgetManager, widgetId: Int) {
        val settings = context.getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE)
        val english = settings.getBoolean("show_english", false)
        // Hand preference: reset sits on the right by default (count on the left); flipping
        // the setting moves the count button to the right, closer to a right thumb.
        val countOnRight = settings.getBoolean("widget_count_on_right", false)
        val count = countPrefs(context).getInt(KEY_COUNT, 0)
        val resetLabel = if (english) "Reset" else "Set Semula"

        val views = RemoteViews(context.packageName, R.layout.widget_zikr_counter).apply {
            setTextViewText(R.id.widgetCount, count.toString())
            setTextViewText(
                R.id.widgetHint,
                if (english) "Tap to count" else "Ketuk untuk mengira"
            )
            setTextViewText(R.id.widgetResetLabel, resetLabel)
            setTextViewText(R.id.widgetResetLabelStart, resetLabel)
            setViewVisibility(R.id.widgetResetStart, if (countOnRight) View.VISIBLE else View.GONE)
            setViewVisibility(R.id.widgetReset, if (countOnRight) View.GONE else View.VISIBLE)
            setOnClickPendingIntent(
                R.id.widgetTapZone,
                broadcast(context, ACTION_INCREMENT, widgetId)
            )
            val reset = broadcast(context, ACTION_RESET, widgetId)
            setOnClickPendingIntent(R.id.widgetReset, reset)
            setOnClickPendingIntent(R.id.widgetResetStart, reset)
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
