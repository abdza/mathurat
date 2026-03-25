package com.abdullahsolutions.mathurat.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahsolutions.mathurat.databinding.ItemCalendarDayBinding
import com.abdullahsolutions.mathurat.model.CalendarDay
import com.abdullahsolutions.mathurat.model.CompletionLevel

class CalendarAdapter(
    private var days: List<CalendarDay>
) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    // Colors for morning indicator
    private val morningColorNone = Color.parseColor("#BDBDBD")
    private val morningColorSughra = Color.parseColor("#A5D6A7")
    private val morningColorKubra = Color.parseColor("#1B5E20")

    // Colors for evening indicator
    private val eveningColorNone = Color.parseColor("#BDBDBD")
    private val eveningColorSughra = Color.parseColor("#81C784")
    private val eveningColorKubra = Color.parseColor("#2E7D32")

    inner class DayViewHolder(val binding: ItemCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemCalendarDayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        with(holder.binding) {
            if (day.isPadding) {
                tvDayNumber.text = ""
                tvDayNumber.background = null
                indicatorMorning.setBackgroundColor(Color.TRANSPARENT)
                indicatorEvening.setBackgroundColor(Color.TRANSPARENT)
                return
            }

            tvDayNumber.text = day.dayOfMonth.toString()

            if (day.isToday) {
                tvDayNumber.setTextColor(Color.WHITE)
                tvDayNumber.setBackgroundResource(
                    com.abdullahsolutions.mathurat.R.drawable.bg_today_circle_highlight
                )
            } else {
                tvDayNumber.setTextColor(Color.WHITE)
                tvDayNumber.setBackgroundResource(
                    com.abdullahsolutions.mathurat.R.drawable.bg_today_circle
                )
            }

            val morningColor = when (day.morningLevel()) {
                CompletionLevel.KUBRA -> morningColorKubra
                CompletionLevel.SUGHRA -> morningColorSughra
                CompletionLevel.NONE -> morningColorNone
            }
            val eveningColor = when (day.eveningLevel()) {
                CompletionLevel.KUBRA -> eveningColorKubra
                CompletionLevel.SUGHRA -> eveningColorSughra
                CompletionLevel.NONE -> eveningColorNone
            }

            indicatorMorning.setBackgroundColor(morningColor)
            indicatorEvening.setBackgroundColor(eveningColor)
        }
    }

    override fun getItemCount() = days.size

    fun updateDays(newDays: List<CalendarDay>) {
        days = newDays
        notifyDataSetChanged()
    }
}
