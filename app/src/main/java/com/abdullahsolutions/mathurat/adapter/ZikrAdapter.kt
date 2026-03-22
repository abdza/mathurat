package com.abdullahsolutions.mathurat.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abdullahsolutions.mathurat.R
import com.abdullahsolutions.mathurat.databinding.ItemVersePairBinding
import com.abdullahsolutions.mathurat.databinding.ItemZikrBinding
import com.abdullahsolutions.mathurat.model.ZikrItem

class ZikrAdapter(
    private val items: MutableList<ZikrItem>,
    private val onCountChanged: (id: Int, count: Int) -> Unit,
    var arabicFontSizeSp: Float = 28f,
    var showEnglish: Boolean = false,
    var showTransliteration: Boolean = false,
    var onItemCompleted: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<ZikrAdapter.ZikrViewHolder>() {

    inner class ZikrViewHolder(val binding: ItemZikrBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZikrViewHolder {
        val binding = ItemZikrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZikrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZikrViewHolder, position: Int) {
        val item = items[position]

        with(holder.binding) {
            tvTitle.text = if (showEnglish && item.titleEn.isNotEmpty()) item.titleEn else item.title
            tvTitleMs.visibility = View.VISIBLE
            tvTitleMs.text = if (showEnglish && item.subtitleEn.isNotEmpty()) item.subtitleEn else item.titleMs

            if (item.pairedVerses != null) {
                tvArabic.visibility = View.GONE
                tvTransliteration.visibility = View.GONE
                tvTranslation.visibility = View.GONE
                llVerses.visibility = View.VISIBLE
                bindVerses(holder, item)
            } else {
                tvArabic.visibility = View.VISIBLE
                tvArabic.textSize = arabicFontSizeSp
                tvArabic.text = item.arabic
                tvTransliteration.text = item.transliteration
                tvTransliteration.visibility = if (showTransliteration) View.VISIBLE else View.GONE
                tvTranslation.text = if (showEnglish && item.translationEn.isNotEmpty()) item.translationEn else item.translation
                tvTranslation.visibility = View.VISIBLE
                llVerses.visibility = View.GONE
            }

            updateCounter(holder, item)

            root.setOnClickListener {
                if (item.currentCount < item.targetCount) {
                    item.currentCount++
                    updateCounter(holder, item)
                    animateBump(tvCounter)
                    onCountChanged(item.id, item.currentCount)
                    if (item.isCompleted) {
                        onItemCompleted?.invoke(holder.bindingAdapterPosition)
                    }
                }
            }

            root.setOnLongClickListener {
                if (item.currentCount > 0) {
                    item.currentCount = 0
                    updateCounter(holder, item)
                    onCountChanged(item.id, item.currentCount)
                }
                true
            }
        }
    }

    private fun bindVerses(holder: ZikrViewHolder, item: ZikrItem) {
        val llVerses = holder.binding.llVerses
        val inflater = LayoutInflater.from(llVerses.context)
        llVerses.removeAllViews()

        val verses = item.pairedVerses ?: return
        verses.forEachIndexed { index, entry ->
            val vb = ItemVersePairBinding.inflate(inflater, llVerses, false)

            vb.tvVerseArabic.textSize = arabicFontSizeSp
            vb.tvVerseArabic.text = entry.arabic

            if (showTransliteration && entry.transliteration.isNotEmpty()) {
                vb.tvVerseTranslit.text = entry.transliteration
                vb.tvVerseTranslit.visibility = View.VISIBLE
            } else {
                vb.tvVerseTranslit.visibility = View.GONE
            }

            val translation = if (showEnglish && entry.translationEn.isNotEmpty()) entry.translationEn
                              else entry.translationMs
            if (translation.isNotEmpty()) {
                vb.tvVerseTranslation.text = translation
                vb.tvVerseTranslation.visibility = View.VISIBLE
            } else {
                vb.tvVerseTranslation.visibility = View.GONE
            }

            vb.verseDivider.visibility = if (index < verses.size - 1) View.VISIBLE else View.GONE

            llVerses.addView(vb.root)
        }
    }

    private fun updateCounter(holder: ZikrViewHolder, item: ZikrItem) {
        with(holder.binding) {
            val ctx = root.context
            tvCounter.text = "${item.currentCount}/${item.targetCount}"

            if (item.isCompleted) {
                tvCounter.setBackgroundResource(R.drawable.bg_counter_complete)
                tvCounter.setTextColor(ContextCompat.getColor(ctx, R.color.white))
                ivComplete.visibility = View.VISIBLE
                root.alpha = 0.75f
            } else {
                tvCounter.setBackgroundResource(R.drawable.bg_counter_active)
                tvCounter.setTextColor(ContextCompat.getColor(ctx, R.color.counter_text))
                ivComplete.visibility = View.GONE
                root.alpha = 1.0f
            }

            progressBar.max = item.targetCount
            progressBar.progress = item.currentCount
        }
    }

    private fun animateBump(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f)
        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 150
            start()
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<ZikrItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
