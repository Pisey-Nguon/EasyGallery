package com.example.demo.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fetch_media.StoreAlbumSource
import com.example.demo.adapter.ListenerAdapter
import com.example.demo.databinding.HolderItemAlbumBinding

class ItemAlbumHolder(private val binding: HolderItemAlbumBinding):RecyclerView.ViewHolder(binding.root) {

    fun onBind(storeAlbumSource: StoreAlbumSource,listener:ListenerAdapter?){
        Glide.with(itemView).load(storeAlbumSource.storeMediaSourceList.first().uri).into(binding.itemImageView)
        binding.itemCount.text = "${storeAlbumSource.storeMediaSourceList.size}"
        binding.itemName.text = storeAlbumSource.folderName
        itemView.setOnClickListener {
            listener?.onClickedAlbum(storeAlbumSource = storeAlbumSource)
        }
    }
}