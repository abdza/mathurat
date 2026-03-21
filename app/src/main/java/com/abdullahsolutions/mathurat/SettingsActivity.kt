package com.abdullahsolutions.mathurat

import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
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

        setupLanguageSwitch()
        setupTranslitSwitch()
        setupFontSizeSeekBar()
    }

    private fun setupLanguageSwitch() {
        val switch = binding.switchLanguage
        switch.isChecked = prefs.getBoolean("show_english", false)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("show_english", isChecked).apply()
        }
    }

    private fun setupTranslitSwitch() {
        val switch = binding.switchTranslit
        switch.isChecked = prefs.getBoolean("show_transliteration", false)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("show_transliteration", isChecked).apply()
        }
    }

    private fun setupFontSizeSeekBar() {
        val preview = binding.tvArabicPreview
        val label = binding.tvFontSizeLabel
        val seekBar = binding.seekBarFontSize

        val currentSize = prefs.getFloat("arabic_font_size", 28f)
        seekBar.progress = (currentSize - 16f).toInt()
        preview.textSize = currentSize
        label.text = "Saiz: ${currentSize.toInt()}sp"

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
                val size = (16 + progress).toFloat()
                preview.textSize = size
                label.text = "Saiz: ${size.toInt()}sp"
                prefs.edit().putFloat("arabic_font_size", size).apply()
            }
            override fun onStartTrackingTouch(sb: SeekBar) {}
            override fun onStopTrackingTouch(sb: SeekBar) {}
        })
    }
}
