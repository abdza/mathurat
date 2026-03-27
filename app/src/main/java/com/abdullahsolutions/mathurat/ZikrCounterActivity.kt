package com.abdullahsolutions.mathurat

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.media.ToneGenerator
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.abdullahsolutions.mathurat.databinding.ActivityZikrCounterBinding

class ZikrCounterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZikrCounterBinding
    private val prefs by lazy { getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE) }
    private val countPrefs by lazy { getSharedPreferences("mathurat_zikr_counter", Context.MODE_PRIVATE) }
    private var count = 0
    private var lastTapTime = 0L
    private val TAP_DEBOUNCE_MS = 300L

    private val toneGenerator by lazy {
        ToneGenerator(AudioManager.STREAM_MUSIC, 60)
    }

    private val vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    private val colorNormal by lazy { ContextCompat.getColor(this, R.color.background) }
    private val colorFlash by lazy { ContextCompat.getColor(this, R.color.colorSecondary) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZikrCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val showEnglish = prefs.getBoolean("show_english", false)
        binding.toolbar.title = if (showEnglish) "Zikir Counter" else "Kaunter Zikir"
        binding.tvHint.text = if (showEnglish) "Tap anywhere to count" else "Ketuk untuk mengira"
        binding.fabReset.text = if (showEnglish) "Reset" else "Set Semula"

        count = countPrefs.getInt("count", 0)
        updateDisplay()

        binding.tapZone.setOnClickListener {
            val now = System.currentTimeMillis()
            if (now - lastTapTime < TAP_DEBOUNCE_MS) return@setOnClickListener
            lastTapTime = now
            count++
            updateDisplay()
            playClick()
            flashBackground()
            vibrateForTap(count)
            countPrefs.edit().putInt("count", count).apply()
        }

        binding.fabReset.setOnClickListener {
            count = 0
            updateDisplay()
            countPrefs.edit().putInt("count", 0).apply()
        }
    }

    private fun updateDisplay() {
        binding.tvCount.text = count.toString()
    }

    private fun playClick() {
        try {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 40)
        } catch (e: Exception) {
            // ignore if tone not available
        }
    }

    private fun vibrateForTap(count: Int) {
        when {
            count % 100 == 0 && prefs.getBoolean("vibrate_on_100", true) -> {
                // double pulse at full amplitude
                vibratePattern(longArrayOf(0, 400, 150, 400), 255)
            }
            count % 33 == 0 && prefs.getBoolean("vibrate_on_33", true) -> {
                // strong single pulse at full amplitude
                vibratePattern(longArrayOf(0, 400), 255)
            }
            prefs.getBoolean("vibrate_on_click", false) -> {
                vibratePattern(longArrayOf(0, 30), VibrationEffect.DEFAULT_AMPLITUDE)
            }
        }
    }

    private fun vibratePattern(pattern: LongArray, amplitude: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val amplitudes = IntArray(pattern.size) { i -> if (i % 2 == 0) 0 else amplitude }
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }

    private fun flashBackground() {
        ValueAnimator.ofObject(ArgbEvaluator(), colorFlash, colorNormal).apply {
            duration = 350
            addUpdateListener { animator ->
                binding.tapZone.setBackgroundColor(animator.animatedValue as Int)
            }
            start()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        toneGenerator.release()
    }
}
