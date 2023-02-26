package com.arnava.photohub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arnava.photohub.R
import com.arnava.photohub.data.db.PhotoDb
import com.arnava.photohub.databinding.PhotoLayoutBinding
import com.bumptech.glide.Glide

class PhotoDbListAdapter() : RecyclerView.Adapter<PhotoDbViewHolder>() {

    private var data: List<PhotoDb?> = emptyList()
    fun setData(data: List<PhotoDb?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoDbViewHolder {
        val binding = PhotoLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoDbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoDbViewHolder, position: Int) {
        val photoDb = data[position]
        with(holder.binding) {
            photoLikesCount.text = photoDb?.photoLikesCount.toString()
            photoLikeByUser.isSelected = photoDb?.photoLikeByUser ?: false
            Glide
                .with(holder.itemView)
                .load(photoDb?.url)
                .placeholder(R.drawable.placeholder_photo)
                .into(photoView)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class PhotoDbViewHolder(val binding: PhotoLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)