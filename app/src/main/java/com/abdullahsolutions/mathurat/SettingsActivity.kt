package com.abdullahsolutions.mathurat

import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.abdullahsolutions.mathurat.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val prefs by lazy { getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val isEnglish = prefs.getBoolean("show_english", false)
        applyLanguage(isEnglish)
        setupLanguageSwitch()
        setupTranslitSwitch()
        setupFontSizeSeekBar(isEnglish)
        setupVibrateClick()
        setupVibrate33()
        setupVibrate100()
    }

    private fun applyLanguage(en: Boolean) {
        binding.toolbar.title = if (en) "Settings" else "Tetapan"
        binding.tvLabelLanguage.text = if (en) "Translation Language" else "Bahasa Terjemahan"
        binding.tvLabelTranslit.text = if (en) "Transliteration" else "Rumi / Transliterasi"
        binding.tvLabelTranslitDesc.text = if (en) "Show transliteration" else "Papar transliterasi Rumi"
        binding.tvLabelFontSize.text = if (en) "Arabic Font Size" else "Saiz Huruf Arab"
        binding.tvLabelVibration.text = if (en) "Zikir Counter Vibration" else "Getaran Kaunter Zikir"
        binding.tvLabelVibrateClick.text = if (en) "Vibrate on each tap" else "Getaran setiap ketukan"
        binding.tvLabelVibrate33.text = if (en) "Vibrate at every 33" else "Getaran setiap 33"
        binding.tvLabelVibrate100.text = if (en) "Vibrate at every 100" else "Getaran setiap 100"

        // Update font size label with current size
        val currentSize = prefs.getFloat("arabic_font_size", 28f)
        binding.tvFontSizeLabel.text = if (en) "Size: ${currentSize.toInt()}sp" else "Saiz: ${currentSize.toInt()}sp"
    }

    private fun setupLanguageSwitch() {
        val switch = binding.switchLanguage
        switch.isChecked = prefs.getBoolean("show_english", false)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("show_english", isChecked).apply()
            applyLanguage(isChecked)
        }
    }

    private fun setupTranslitSwitch() {
        val switch = binding.switchTranslit
        switch.isChecked = prefs.getBoolean("show_transliteration", false)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("show_transliteration", isChecked).apply()
        }
    }

    private fun setupVibrateClick() {
        val switch = binding.switchVibrateClick
        switch.isChecked = prefs.getBoolean("vibrate_on_click", false)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("vibrate_on_click", isChecked).apply()
        }
    }

    private fun setupVibrate33() {
        val switch = binding.switchVibrate33
        switch.isChecked = prefs.getBoolean("vibrate_on_33", true)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("vibrate_on_33", isChecked).apply()
        }
    }

    private fun setupVibrate100() {
        val switch = binding.switchVibrate100
        switch.isChecked = prefs.getBoolean("vibrate_on_100", true)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("vibrate_on_100", isChecked).apply()
        }
    }

    private fun setupFontSizeSeekBar(initiallyEnglish: Boolean) {
        val preview = binding.tvArabicPreview
        val label = binding.tvFontSizeLabel
        val seekBar = binding.seekBarFontSize

        val currentSize = prefs.getFloat("arabic_font_size", 28f)
        seekBar.progress = (currentSize - 16f).toInt()
        preview.textSize = currentSize

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
                val size = (16 + progress).toFloat()
                preview.textSize = size
                val en = prefs.getBoolean("show_english", false)
                label.text = if (en) "Size: ${size.toInt()}sp" else "Saiz: ${size.toInt()}sp"
                prefs.edit().putFloat("arabic_font_size", size).apply()
            }
            override fun onStartTrackingTouch(sb: SeekBar) {}
            override fun onStopTrackingTouch(sb: SeekBar) {}
        })
    }
}
