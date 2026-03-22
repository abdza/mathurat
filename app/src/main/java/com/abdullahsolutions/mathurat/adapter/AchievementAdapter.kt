package com.abdullahsolutions.mathurat.adapter

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahsolutions.mathurat.AchievementManager
import com.abdullahsolutions.mathurat.databinding.ItemAchievementBinding
import com.abdullahsolutions.mathurat.model.Achievement
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AchievementAdapter(
    private val achievements: List<Achievement>,
    private val context: Context,
    private val showEnglish: Boolean
) : RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {

    private val displayFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

    inner class ViewHolder(val binding: ItemAchievementBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAchievementBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ach = achievements[position]
        val earnedAt = AchievementManager.getEarnedDate(ach.id, context)
        val earned = earnedAt != null

        with(holder.binding) {
            tvEmoji.text = if (earned) ach.emoji else "🔒"
            tvTitle.text = if (showEnglish) ach.titleEn else ach.titleMs
            tvDesc.text = if (showEnglish) ach.descEn else ach.descMs

            if (earned) {
                tvDate.text = displayFormat.format(Date(earnedAt!!))
                root.alpha = 1f
            } else {
                tvDate.text = if (showEnglish) "Locked" else "Belum dicapai"
                root.alpha = 0.45f
            }
        }
    }

    override fun getItemCount() = achievements.size
}
