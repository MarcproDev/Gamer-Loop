package com.example.gamerloopactivity.ui.contentscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.gamerloopactivity.domain.ContentInfo
import androidx.recyclerview.widget.ListAdapter
import com.example.gamerloopactivity.databinding.ItemContentBinding

class ContentScreenAdapter : ListAdapter<ContentInfo, ContentScreenViewHolder>(ContentInfoDiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentScreenViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentScreenViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: ContentScreenViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object ContentInfoDiffCallback : DiffUtil.ItemCallback<ContentInfo>() {
    override fun areItemsTheSame(oldItem: ContentInfo, newItem: ContentInfo): Boolean {
        return oldItem.title == newItem.title
    }
    override fun areContentsTheSame(oldItem: ContentInfo, newItem: ContentInfo): Boolean {
        return oldItem == newItem
    }
}