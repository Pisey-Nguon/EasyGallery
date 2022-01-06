package com.example.demo.ui.gallery

import androidx.lifecycle.ViewModel
import com.example.fetch_media.DocumentRepo

class GalleryViewModel:ViewModel() {

    private val TAG = this.javaClass.name
    var documentRepo= DocumentRepo()


}