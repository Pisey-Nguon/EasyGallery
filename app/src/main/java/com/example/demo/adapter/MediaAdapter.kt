package com.example.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fetch_media.StoreMediaSource
import com.example.fetch_media.StoreMediaViewType
import com.example.demo.databinding.HolderItemImageBinding
import com.example.demo.databinding.HolderItemVideoBinding
import com.example.demo.viewholder.ItemImageHolder
import com.example.demo.viewholder.ItemVideoHolder


class MediaAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listImage = ArrayList<StoreMediaSource>()
    companion object{
        const val IMAGE_POSITION = 0
        const val VIDEO_POSITION = 1
    }

    fun updateItems(newListImage: List<StoreMediaSource>) {
        val diffCallback = MediaDiffCallback(
            this.listImage,
            newListImage
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listImage.clear()
        this.listImage.addAll(newListImage)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when(listImage[position].viewType){
            StoreMediaViewType.VIDEO -> VIDEO_POSITION
            StoreMediaViewType.IMAGE -> IMAGE_POSITION
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIDEO_POSITION -> ItemVideoHolder(HolderItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            IMAGE_POSITION -> ItemImageHolder(HolderItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else -> throw IllegalArgumentException("no view type that match")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ItemVideoHolder -> holder.onBind(listImage[holder.adapterPosition])
            is ItemImageHolder -> holder.onBind(listImage[holder.adapterPosition])
            else -> throw IllegalArgumentException("No bind view holder")
        }
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

}