package com.abdullahsolutions.mathurat

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullahsolutions.mathurat.adapter.ReferenceAdapter
import com.abdullahsolutions.mathurat.data.ReferenceData
import com.abdullahsolutions.mathurat.databinding.ActivityReferenceDetailBinding

class ReferenceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReferenceDetailBinding
    private val settingsPrefs by lazy { getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferenceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val categoryId = intent.getIntExtra("category_id", 1)
        val showEnglish = settingsPrefs.getBoolean("show_english", false)
        val arabicFontSize = settingsPrefs.getFloat("arabic_font_size", 24f)

        val category = ReferenceData.categories.first { it.id == categoryId }

        binding.toolbar.title = if (showEnglish && category.titleEn.isNotEmpty()) category.titleEn else category.title

        val adapter = ReferenceAdapter(category.entries, showEnglish, arabicFontSize)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
