package com.syahrul.danamonfrontendtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syahrul.danamonfrontendtest.databinding.ItemPhotoListBinding
import com.syahrul.danamonfrontendtest.model.Photos

class PhotoListAdapter (
    private val context: Context,
    private val photoList: ArrayList<Photos>
) : RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>() {

    fun refreshData(newPhotoList: List<Photos>){
        photoList.addAll(newPhotoList)
        notifyDataSetChanged()
    }

    fun getPhotoListSize() = photoList.size

    class PhotoListViewHolder(val binding: ItemPhotoListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        return PhotoListViewHolder(
            ItemPhotoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val photo = photoList[position]

        holder.binding.tvId.text = "${photo.id}"
        holder.binding.tvAlbumId.text = "${photo.albumId}"
        holder.binding.tvTitle.text = "${photo.title}"

        Glide.with(context).load(photo.thumbnailUrl).into(holder.binding.thumbnailImage)
    }

    override fun getItemCount() = photoList.size

}