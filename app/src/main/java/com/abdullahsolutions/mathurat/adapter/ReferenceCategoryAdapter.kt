package com.abdullahsolutions.mathurat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullahsolutions.mathurat.databinding.ItemReferenceCategoryBinding
import com.abdullahsolutions.mathurat.model.ReferenceCategory

class ReferenceCategoryAdapter(
    private val categories: List<ReferenceCategory>,
    var showEnglish: Boolean = false,
    private val onClick: (ReferenceCategory) -> Unit
) : RecyclerView.Adapter<ReferenceCategoryAdapter.Holder>() {

    inner class Holder(val binding: ItemReferenceCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemReferenceCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cat = categories[position]
        holder.binding.tvTitle.text = if (showEnglish && cat.titleEn.isNotEmpty()) cat.titleEn else cat.title
        holder.binding.tvSubtitle.text = if (showEnglish && cat.subtitleEn.isNotEmpty()) cat.subtitleEn else cat.subtitle
        holder.binding.root.setOnClickListener { onClick(cat) }
    }

    override fun getItemCount() = categories.size
}
