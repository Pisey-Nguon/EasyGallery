package com.example.fetch_media

import android.net.Uri
import java.util.*

data class StoreMediaSource(val uri:Uri, val name:String,val bucketId:String,val bucketName:String, val date:Date,val viewType:StoreMediaViewType)
