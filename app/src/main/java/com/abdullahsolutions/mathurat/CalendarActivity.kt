package com.abdullahsolutions.mathurat

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.abdullahsolutions.mathurat.adapter.CalendarAdapter
import com.abdullahsolutions.mathurat.databinding.ActivityCalendarBinding
import com.abdullahsolutions.mathurat.model.CalendarDay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var adapter: CalendarAdapter

    private val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    private val dateKeyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val displayCal = Calendar.getInstance()

    private val settingsPrefs by lazy {
        getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE)
    }
    private val compPrefs by lazy {
        getSharedPreferences("mathurat_completions", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(null)

        // Set display to the 1st of current month
        displayCal.set(Calendar.DAY_OF_MONTH, 1)

        adapter = CalendarAdapter(emptyList())
        binding.rvCalendar.layoutManager = GridLayoutManager(this, 7)
        binding.rvCalendar.adapter = adapter

        binding.btnPrevMonth.setOnClickListener {
            displayCal.add(Calendar.MONTH, -1)
            render()
        }
        binding.btnNextMonth.setOnClickListener {
            displayCal.add(Calendar.MONTH, 1)
            render()
        }

        render()
        updateLegend()
    }

    private fun render() {
        binding.tvMonthYear.text = monthYearFormat.format(displayCal.time)

        val days = buildDays()
        adapter.updateDays(days)
    }

    private fun buildDays(): List<CalendarDay> {
        val cal = displayCal.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)

        val todayCal = Calendar.getInstance()
        val todayStr = dateKeyFormat.format(todayCal.time)

        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        // DAY_OF_WEEK: 1=Sunday ... 7=Saturday
        val firstDow = cal.get(Calendar.DAY_OF_WEEK) // 1..7
        val paddingBefore = firstDow - 1

        val result = mutableListOf<CalendarDay>()

        // Padding cells before the 1st
        repeat(paddingBefore) {
            result.add(CalendarDay(dateString = "", dayOfMonth = 0))
        }

        for (d in 1..daysInMonth) {
            cal.set(Calendar.DAY_OF_MONTH, d)
            val ds = dateKeyFormat.format(cal.time)

            val sughraMorning = compPrefs.getBoolean("comp_SUGHRA_MORNING_$ds", false)
            val sughraEvening = compPrefs.getBoolean("comp_SUGHRA_EVENING_$ds", false)
            val kubraMorning = compPrefs.getBoolean("comp_KUBRA_MORNING_$ds", false)
            val kubraEvening = compPrefs.getBoolean("comp_KUBRA_EVENING_$ds", false)

            result.add(
                CalendarDay(
                    dateString = ds,
                    dayOfMonth = d,
                    sughraMorning = sughraMorning,
                    sughraEvening = sughraEvening,
                    kubraMorning = kubraMorning,
                    kubraEvening = kubraEvening,
                    isToday = ds == todayStr
                )
            )
        }

        return result
    }

    private fun updateLegend() {
        val en = settingsPrefs.getBoolean("show_english", false)
        if (en) {
            binding.tvLegendTitle.text = "Legend"
            binding.tvLegendSughraMorning.text = "Morning Sughra"
            binding.tvLegendKubraMorning.text = "Morning Kubra"
            binding.tvLegendSughraEvening.text = "Evening Sughra"
            binding.tvLegendKubraEvening.text = "Evening Kubra"
            binding.tvLegendNone.text = "None"
        }
        // Malay is already the default in the layout
    }
}
