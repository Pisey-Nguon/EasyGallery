package com.example.demo.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fetch_media.StoreMediaSource
import com.example.demo.databinding.HolderItemVideoBinding

class ItemVideoHolder(private val binding:HolderItemVideoBinding):RecyclerView.ViewHolder(binding.root) {
    fun onBind(storeMediaSource: StoreMediaSource){
        Glide.with(binding.root.context).load(storeMediaSource.uri).into(binding.itemImageView)
    }
}