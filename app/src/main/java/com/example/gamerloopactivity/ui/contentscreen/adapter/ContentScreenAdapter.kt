package com.example.gamerloopactivity.ui.contentscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gamerloopactivity.databinding.ItemContentBinding
import com.example.gamerloopactivity.domain.ContentInfo
import com.squareup.picasso.Picasso

class ContentScreenAdapter(
    private val onClickListener: (gameId: String) -> Unit
) : ListAdapter<ContentInfo, ContentScreenAdapter.ContentScreenViewHolder>(ContentInfoDiffCallback) {

    inner class ContentScreenViewHolder(private val binding: ItemContentBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentInfo) {
            binding.tvContentName.text = item.title
            binding.tvShortDescription.text = item.shortDescription
            val thumbnail = item.thumbnail
            Picasso.get()
                .load(thumbnail)
                .into(binding.ivGame)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentScreenViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentScreenViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ContentScreenViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener(item.id.toString())
        }
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