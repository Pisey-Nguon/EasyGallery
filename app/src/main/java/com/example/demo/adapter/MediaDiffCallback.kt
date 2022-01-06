package com.example.demo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fetch_media.StoreMediaSource

class MediaDiffCallback(
    private val mOldImageList: List<StoreMediaSource>,
    private val mNewImageList: List<StoreMediaSource>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldImageList.size
    }

    override fun getNewListSize(): Int {
        return mNewImageList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldImageList[oldItemPosition].uri === mNewImageList[newItemPosition].uri
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, name) = mOldImageList[oldItemPosition]
        val (_, name1) = mNewImageList[newItemPosition]
        return name == name1
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}