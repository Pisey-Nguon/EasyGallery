package com.example.demo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.ui.gallery.GalleryActivity
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenGallery.setOnClickListener {
            runWithPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE){
                startActivity(Intent(this,GalleryActivity::class.java))
            }
        }

    }

}