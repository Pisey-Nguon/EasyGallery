package com.example.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fetch_media.StoreAlbumSource
import com.example.demo.databinding.HolderItemAlbumBinding
import com.example.demo.databinding.HolderItemLoadingBinding
import com.example.demo.viewholder.ItemAlbumHolder
import com.example.demo.viewholder.ItemLoadingHolder

class AlbumAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val albumList = ArrayList<StoreAlbumSource>()

    private var listener:ListenerAdapter? = null
    init {
        albumList.add(StoreAlbumSource(folderName = "",storeMediaSourceList = ArrayList()))
    }
    companion object{
        const val ALBUM_POSITION = 0
        const val LOADING_POSITION = 1
    }
    fun setListener(listener:ListenerAdapter){
        this.listener = listener
    }
    fun updateItems(newAlbumList:ArrayList<StoreAlbumSource>){
        val diffCallback = AlbumDiffCallback(albumList,newAlbumList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        albumList.clear()
        albumList.addAll(newAlbumList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when(albumList[position].folderName){
            "" -> LOADING_POSITION
            else -> ALBUM_POSITION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ALBUM_POSITION -> ItemAlbumHolder(HolderItemAlbumBinding.inflate(getInflater(parent),parent,false))
            LOADING_POSITION -> ItemLoadingHolder(HolderItemLoadingBinding.inflate(getInflater(parent),parent,false))
            else -> throw IllegalArgumentException("no view holder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ItemAlbumHolder -> holder.onBind(albumList[holder.adapterPosition],listener)
            is ItemLoadingHolder -> holder.onBind()
        }
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    private fun getInflater(parent: ViewGroup):LayoutInflater{
        return LayoutInflater.from(parent.context)
    }
}