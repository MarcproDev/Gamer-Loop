package com.example.gamerloopactivity.ui.contentscreen.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gamerloopactivity.R
import com.example.gamerloopactivity.databinding.ItemContentBinding
import com.example.gamerloopactivity.domain.ContentInfo
import com.squareup.picasso.Picasso

class ContentScreenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemContentBinding.bind(view)
    fun bind(item: ContentInfo) {
        binding.tvContentName.text = item.title
        binding.tvShortDescription.text = item.shortDescription
        Picasso.get()
            .load(item.thumbnail)
            .into(binding.ivGame)
    }
}