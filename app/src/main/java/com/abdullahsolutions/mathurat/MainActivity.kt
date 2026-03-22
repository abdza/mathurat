package com.abdullahsolutions.mathurat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.abdullahsolutions.mathurat.adapter.ZikrAdapter
import com.abdullahsolutions.mathurat.data.ZikrData
import com.abdullahsolutions.mathurat.databinding.ActivityMainBinding
import com.abdullahsolutions.mathurat.model.Achievement
import com.abdullahsolutions.mathurat.model.Session
import com.abdullahsolutions.mathurat.model.Version
import com.abdullahsolutions.mathurat.model.ZikrItem
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ZikrAdapter
    private val currentItems = mutableListOf<ZikrItem>()

    private var currentSession: Session = Session.MORNING
    private var currentVersion: Version = Version.SUGHRA

    private val prefs by lazy { getSharedPreferences("mathurat_counts", Context.MODE_PRIVATE) }
    private val settingsPrefs by lazy { getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE) }

    // Prevent showing achievement popup multiple times per session
    private var cycleAwardedToday = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        currentSession = detectSession()

        setupRecyclerView()
        setupTabs()
        setupVersionToggle()
        updateTabLabels()
        loadContent()
    }

    override fun onResume() {
        super.onResume()
        adapter.arabicFontSizeSp = settingsPrefs.getFloat("arabic_font_size", 28f)
        adapter.showEnglish = settingsPrefs.getBoolean("show_english", false)
        adapter.showTransliteration = settingsPrefs.getBoolean("show_transliteration", false)
        adapter.notifyDataSetChanged()
        updateTabLabels()
        invalidateOptionsMenu()
        loadContent()
    }

    private fun detectSession(): Session {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return if (hour in 4..11) Session.MORNING else Session.EVENING
    }

    private fun setupRecyclerView() {
        adapter = ZikrAdapter(
            currentItems,
            { id, count ->
                saveCount(id, count)
                checkCycleCompletion()
            },
            settingsPrefs.getFloat("arabic_font_size", 28f),
            settingsPrefs.getBoolean("show_english", false),
            settingsPrefs.getBoolean("show_transliteration", false),
            onItemCompleted = { position ->
                val nextPosition = position + 1
                if (nextPosition < adapter.itemCount) {
                    val scroller = object : androidx.recyclerview.widget.LinearSmoothScroller(this@MainActivity) {
                        override fun getVerticalSnapPreference() = SNAP_TO_START
                    }
                    scroller.targetPosition = nextPosition
                    binding.recyclerView.layoutManager?.startSmoothScroll(scroller)
                }
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            setHasFixedSize(false)
        }
    }

    private fun checkCycleCompletion() {
        if (cycleAwardedToday) return
        if (currentItems.isNotEmpty() && currentItems.all { it.isCompleted }) {
            cycleAwardedToday = true
            val newAchievements = AchievementManager.recordAndCheck(currentVersion, currentSession, this)
            if (newAchievements.isNotEmpty()) {
                showAchievementDialog(newAchievements)
            }
        }
    }

    private fun showAchievementDialog(achievements: List<Achievement>) {
        val en = settingsPrefs.getBoolean("show_english", false)
        // Show one at a time, chained
        fun showNext(index: Int) {
            if (index >= achievements.size) return
            val ach = achievements[index]
            val title = if (en) ach.titleEn else ach.titleMs
            val desc = if (en) ach.descEn else ach.descMs
            val header = if (en) "Achievement Unlocked!" else "Pencapaian Baharu!"
            AlertDialog.Builder(this)
                .setTitle("${ach.emoji}  $header")
                .setMessage("$title\n\n$desc")
                .setPositiveButton("OK") { _, _ -> showNext(index + 1) }
                .setCancelable(false)
                .show()
        }
        showNext(0)
    }

    private fun setupTabs() {
        binding.tabLayout.getTabAt(if (currentSession == Session.MORNING) 0 else 1)?.select()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentSession = if (tab?.position == 0) Session.MORNING else Session.EVENING
                cycleAwardedToday = false
                loadContent()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupVersionToggle() {
        binding.toggleVersion.check(R.id.btnSughra)

        binding.toggleVersion.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                currentVersion = if (checkedId == R.id.btnSughra) Version.SUGHRA else Version.KUBRA
                cycleAwardedToday = false
                loadContent()
            }
        }
    }

    private fun loadContent() {
        val items = ZikrData.getZikr(currentSession, currentVersion)
        items.forEach { item ->
            item.currentCount = getSavedCount(item.id)
        }
        adapter.updateItems(items)
        currentItems.clear()
        currentItems.addAll(items)

        val showEnglish = settingsPrefs.getBoolean("show_english", false)
        val sessionLabel = if (currentSession == Session.MORNING) {
            if (showEnglish) "Morning" else "Pagi"
        } else {
            if (showEnglish) "Evening" else "Petang"
        }
        val versionLabel = if (currentVersion == Version.SUGHRA) "Sughra" else "Kubra"
        supportActionBar?.subtitle = "Al-Mathurat $sessionLabel • $versionLabel"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        updateMenuTitles(menu)
        return true
    }

    private fun updateMenuTitles(menu: Menu) {
        val en = settingsPrefs.getBoolean("show_english", false)
        menu.findItem(R.id.action_calendar)?.title = if (en) "Calendar" else "Kalendar"
        menu.findItem(R.id.action_achievements)?.title = if (en) "Achievements" else "Pencapaian"
        menu.findItem(R.id.action_counter)?.title = if (en) "Zikir Counter" else "Kaunter Zikir"
        menu.findItem(R.id.action_reference)?.title = if (en) "Reference" else "Rujukan"
        menu.findItem(R.id.action_settings)?.title = if (en) "Settings" else "Tetapan"
        menu.findItem(R.id.action_reset_all)?.title = if (en) "Reset All Counters" else "Set Semula Semua Kiraan"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_calendar -> {
                startActivity(Intent(this, CalendarActivity::class.java))
                true
            }
            R.id.action_achievements -> {
                startActivity(Intent(this, AchievementsActivity::class.java))
                true
            }
            R.id.action_counter -> {
                startActivity(Intent(this, ZikrCounterActivity::class.java))
                true
            }
            R.id.action_reference -> {
                startActivity(Intent(this, ReferenceListActivity::class.java))
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_reset_all -> {
                resetAllCounts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateTabLabels() {
        val showEnglish = settingsPrefs.getBoolean("show_english", false)
        binding.tabLayout.getTabAt(0)?.text = if (showEnglish) "Morning" else "Pagi"
        binding.tabLayout.getTabAt(1)?.text = if (showEnglish) "Evening" else "Petang"
    }

    private fun resetAllCounts() {
        currentItems.forEach { item ->
            item.currentCount = 0
            saveCount(item.id, 0)
        }
        adapter.notifyDataSetChanged()
        cycleAwardedToday = false
        binding.recyclerView.scrollToPosition(0)
    }

    private fun countKey(id: Int): String = "${currentSession.name}_${currentVersion.name}_$id"
    private fun saveCount(id: Int, count: Int) = prefs.edit().putInt(countKey(id), count).apply()
    private fun getSavedCount(id: Int): Int = prefs.getInt(countKey(id), 0)
}
