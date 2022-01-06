package com.example.demo.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fetch_media.StoreMediaSource
import com.example.demo.databinding.HolderItemImageBinding

class ItemImageHolder(private val binding: HolderItemImageBinding):RecyclerView.ViewHolder(binding.root) {

    fun onBind(storeMediaSource: StoreMediaSource){
        Glide.with(itemView).load(storeMediaSource.uri).into(binding.itemImageView)
    }
}