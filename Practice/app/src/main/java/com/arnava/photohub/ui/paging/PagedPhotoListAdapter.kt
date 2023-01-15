package com.arnava.photohub.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.photohub.R
import com.arnava.photohub.data.models.dto.Photo
import com.arnava.photohub.databinding.PhotoLayoutBinding
import com.bumptech.glide.Glide

class PagedPhotoListAdapter(
    private val onClick: (Photo) -> Unit
) : PagingDataAdapter<Photo, PhotoListViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val binding = PhotoLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val item = getItem(position)
        Glide
            .with(holder.itemView)
            .load(item?.urls?.regular)
            .placeholder(R.drawable.placeholder_photo)
            .into(holder.binding.photoView)

        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(it)
            }
        }
    }
}

class PhotoListViewHolder(val binding: PhotoLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
}
