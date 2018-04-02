package com.example.vamp.photogallary.adapter

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.vamp.photogallary.R
import com.example.vamp.photogallary.model.ImageModel
import com.squareup.picasso.Picasso
import java.io.File

class CustomRecyclerAdapter(imageModel:ArrayList<ImageModel>):RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>() {
    var imgModel = imageModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imgModel.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val model = imgModel[position]
        Picasso.get().load(File(model.mPath)).into(holder.imageView)
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.img_view)
    }
}