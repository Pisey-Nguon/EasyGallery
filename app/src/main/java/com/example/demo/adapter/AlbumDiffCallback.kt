package com.example.demo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fetch_media.StoreAlbumSource

class AlbumDiffCallback(private val mOldAlbumList:ArrayList<StoreAlbumSource>,private val mNewAlbumList:ArrayList<StoreAlbumSource>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldAlbumList.size
    }

    override fun getNewListSize(): Int {
        return mNewAlbumList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldAlbumList[oldItemPosition].folderName == mNewAlbumList[newItemPosition].folderName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, name) = mOldAlbumList[oldItemPosition]
        val (_, name1) = mNewAlbumList[newItemPosition]
        return name == name1
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}