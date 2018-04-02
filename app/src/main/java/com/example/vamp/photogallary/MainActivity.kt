package com.example.vamp.photogallary

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.example.vamp.photogallary.adapter.CustomRecyclerAdapter
import com.example.vamp.photogallary.model.ImageModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE = 1

    var customRecyclerAdapter:CustomRecyclerAdapter?=null
    var imageModel:ArrayList<ImageModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val hasPermission = ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE)
        if(hasPermission!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
        }else{
            RetrievingImages()
            settingUpAdapter()
        }

    }

    fun RetrievingImages(){
        val imgCur = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,null)

        while (imgCur!=null && imgCur.moveToNext()){
            var imagePath = imgCur.getString(imgCur.getColumnIndex(MediaStore.Images.Media.DATA))
            imageModel.add(ImageModel(imagePath))
        }
        imgCur.close()
    }

    fun settingUpAdapter(){
        customRecyclerAdapter = CustomRecyclerAdapter(imageModel)
        val layoutManager = GridLayoutManager(applicationContext,3)
        main_recyclerview.layoutManager = layoutManager
        main_recyclerview.adapter = customRecyclerAdapter
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_CODE){
            if((grantResults[0] and grantResults.size)==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Permission Granted",Toast.LENGTH_SHORT).show()
                //retriving and setting up adapter on first run to handle crash
                RetrievingImages()
                settingUpAdapter()
            }else{
                Toast.makeText(applicationContext,"Permission Granted",Toast.LENGTH_SHORT).show()
            }
        }
    }
}

