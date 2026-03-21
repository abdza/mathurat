package com.abdullahsolutions.mathurat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdullahsolutions.mathurat.R
import com.abdullahsolutions.mathurat.model.EntryType
import com.abdullahsolutions.mathurat.model.ReferenceEntry

class ReferenceAdapter(
    private val entries: List<ReferenceEntry>,
    var showEnglish: Boolean = false,
    var arabicFontSizeSp: Float = 24f
) : RecyclerView.Adapter<ReferenceAdapter.EntryHolder>() {

    companion object {
        const val TYPE_HEADING = 0
        const val TYPE_INSTRUCTION = 1
        const val TYPE_ARABIC = 2
        const val TYPE_TRANSLITERATION = 3
        const val TYPE_TRANSLATION = 4
        const val TYPE_NOTE = 5
    }

    inner class EntryHolder(itemView: View, val textView: TextView) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int) = when (entries[position].type) {
        EntryType.SECTION_HEADING -> TYPE_HEADING
        EntryType.INSTRUCTION -> TYPE_INSTRUCTION
        EntryType.ARABIC -> TYPE_ARABIC
        EntryType.TRANSLITERATION -> TYPE_TRANSLITERATION
        EntryType.TRANSLATION -> TYPE_TRANSLATION
        EntryType.NOTE -> TYPE_NOTE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADING -> {
                val v = inflater.inflate(R.layout.item_ref_section_heading, parent, false)
                EntryHolder(v, v as TextView)
            }
            TYPE_INSTRUCTION -> {
                val v = inflater.inflate(R.layout.item_ref_instruction, parent, false)
                EntryHolder(v, v as TextView)
            }
            TYPE_ARABIC -> {
                val v = inflater.inflate(R.layout.item_ref_arabic, parent, false)
                EntryHolder(v, v.findViewById(R.id.tvArabic))
            }
            TYPE_TRANSLITERATION -> {
                val v = inflater.inflate(R.layout.item_ref_transliteration, parent, false)
                EntryHolder(v, v as TextView)
            }
            TYPE_TRANSLATION -> {
                val v = inflater.inflate(R.layout.item_ref_translation, parent, false)
                EntryHolder(v, v as TextView)
            }
            else -> {
                val v = inflater.inflate(R.layout.item_ref_note, parent, false)
                EntryHolder(v, v as TextView)
            }
        }
    }

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        val entry = entries[position]
        val text = if (showEnglish && entry.contentEn.isNotEmpty()) entry.contentEn else entry.content
        holder.textView.text = text
        if (entry.type == EntryType.ARABIC) {
            holder.textView.textSize = arabicFontSizeSp
        }
    }

    override fun getItemCount() = entries.size
}
