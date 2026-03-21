package com.abdullahsolutions.mathurat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullahsolutions.mathurat.adapter.ReferenceCategoryAdapter
import com.abdullahsolutions.mathurat.data.ReferenceData
import com.abdullahsolutions.mathurat.databinding.ActivityReferenceListBinding

class ReferenceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReferenceListBinding
    private val settingsPrefs by lazy { getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferenceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val showEnglish = settingsPrefs.getBoolean("show_english", false)
        binding.toolbar.title = if (showEnglish) "Reference" else "Rujukan"

        val adapter = ReferenceCategoryAdapter(ReferenceData.categories, showEnglish) { category ->
            val intent = Intent(this, ReferenceDetailActivity::class.java)
            intent.putExtra("category_id", category.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
