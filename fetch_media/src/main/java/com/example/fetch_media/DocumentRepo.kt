package com.example.fetch_media

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class DocumentRepo {

    @SuppressLint("Recycle", "InlinedApi")
    fun getCursor(context: Context):Cursor?{
        val uri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.BUCKET_ID,
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
        )
        // Return only video and image metadata.
        val selection =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        return context.contentResolver.query(uri, projection, selection, null, sortOrder)
    }

    @SuppressLint("InlinedApi")
    fun getImageOneByOne(context: Context) = liveData(Dispatchers.IO) {
        val imageCursor = getCursor(context)
        val imageIdColumn = imageCursor?.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
        val imageDataColumn = imageCursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        val imageDateModifiedColumn = imageCursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
        val imageDisplayNameColumn = imageCursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
        val bucketIdColumn = imageCursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_ID)
        val bucketNameColumn = imageCursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
        withContext(Dispatchers.IO) {
            outer@while (imageCursor?.moveToNext() == true) {
                val id = imageIdColumn?.let { imageCursor.getLong(it) }

                //for uri can use data or content uri
                val data = imageDataColumn?.let { imageCursor.getString(it) }
                val dateModified = Date(TimeUnit.SECONDS.toMillis(imageCursor.getLong(imageDateModifiedColumn ?: 0)))
                val displayName = imageDisplayNameColumn?.let { imageCursor.getString(it) }
                val bucketId = bucketIdColumn?.let { imageCursor.getString(it) }
                val bucketName = bucketNameColumn?.let { imageCursor.getString(it) }
                val contentUri = id?.let {
                    ContentUris.withAppendedId(
                        MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                        it
                    )
                }
                contentUri?.let {
                    emit(StoreMediaSource(uri = contentUri,name = displayName.toString(),bucketId = bucketId.toString(),bucketName = bucketName.toString(),date = dateModified,viewType = getType(context,it)))
                }
            }

        }
    }


    fun getAlbums(context: Context,lifecycleOwner: LifecycleOwner,callBack:(result:ArrayList<StoreAlbumSource>) -> Unit) {
        val albumList = ArrayList<StoreAlbumSource>()
        val imageList = ArrayList<StoreMediaSource>()
        val cursor = getCursor(context)
        var count = 0
        getImageOneByOne(context).observe(lifecycleOwner){ storeMediaSource ->
            count++
            imageList.add(storeMediaSource)
            if (count == cursor?.count){
                albumList.add(StoreAlbumSource(folderName = "Gallery",storeMediaSourceList = imageList.clone() as ArrayList<StoreMediaSource>))
                outer@while(true){
                    for(mediaSource in imageList) {
                        val result = imageList.filter { it.bucketId == mediaSource.bucketId }
                        albumList.add(StoreAlbumSource(folderName = mediaSource.bucketName,storeMediaSourceList = result.toCollection(ArrayList())))
                        imageList.removeAll(result)
                        continue@outer
                    }
                    callBack.invoke(albumList)
                    break
                }
            }
        }
    }



    private fun getType(context: Context,uri:Uri):StoreMediaViewType{
        val mimType = context.contentResolver.getType(uri)
        return when {
            mimType?.startsWith("image") == true -> {
                StoreMediaViewType.IMAGE
            }
            mimType?.startsWith("video") == true -> {
                StoreMediaViewType.VIDEO
            }
            else -> {
                StoreMediaViewType.VIDEO
            }
        }
    }
}