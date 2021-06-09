package com.nerva.io.fruitidentifierapp.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.nerva.io.fruitidentifierapp.databinding.ActivityFragmentScannerBinding

@Suppress("DEPRECATION")
class ScannerActivity  : AppCompatActivity(){
    private lateinit var binding : ActivityFragmentScannerBinding
    companion object{
        const val REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.take.setOnClickListener {
           capturePhoto()
        }
    }
    private fun capturePhoto(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            binding.captureImage.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
    }
}