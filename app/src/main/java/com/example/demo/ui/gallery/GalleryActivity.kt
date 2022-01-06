package com.example.demo.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.adapter.AlbumAdapter
import com.example.demo.adapter.ListenerAdapter
import com.example.demo.adapter.MediaAdapter
import com.example.demo.databinding.ActivityGalleryBinding
import com.example.fetch_media.StoreAlbumSource
import com.example.fetch_media.StoreMediaSource

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private val mediaList = ArrayList<StoreMediaSource>()
    private val mediaAdapter = MediaAdapter()
    private val albumAdapter = AlbumAdapter()
    private val vm : GalleryViewModel by viewModels()
    private val TAG = this.javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvListImage.layoutManager = GridLayoutManager(this,3)
        binding.rvListImage.adapter = mediaAdapter

        binding.rvListAlbum.layoutManager = LinearLayoutManager(this)
        binding.rvListAlbum.adapter = albumAdapter

        albumAdapter.setListener(object:ListenerAdapter{
            override fun onClickedAlbum(storeAlbumSource: StoreAlbumSource) {
                binding.rvListAlbum.visibility = View.GONE
                binding.btnOpenAlbums.text = storeAlbumSource.folderName
                mediaAdapter.updateItems(storeAlbumSource.storeMediaSourceList)
            }
        })

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.btnOpenAlbums.setOnClickListener {
            toggleVisibleAlbum()
        }
        
        vm.documentRepo.getImageOneByOne(this).observe(this,{
            mediaList.add(it)
            mediaAdapter.updateItems(mediaList)
        })

        vm.documentRepo.getAlbums(this,this){
            albumAdapter.updateItems(it)
        }

    }

    private fun toggleVisibleAlbum(){
        if (binding.rvListAlbum.visibility == View.VISIBLE){
            binding.rvListAlbum.visibility = View.GONE
        }else{
            binding.rvListAlbum.visibility = View.VISIBLE
        }
    }


}