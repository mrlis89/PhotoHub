package com.arnava.photohub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.PhotoLayoutBinding
import com.bumptech.glide.Glide

class PagedPhotoListAdapter (
    private val onPhotoClick: (UnsplashPhoto) -> Unit,
    private val onLikeClick: (UnsplashPhoto) -> Unit,
) : PagingDataAdapter<UnsplashPhoto, PhotoListViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val binding = PhotoLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val photo = getItem(position)
        with(holder.binding) {
            photoLikesCount.text = photo?.likes.toString()
            photoLikeByUser.isSelected = photo?.likedByUser ?: false
            photoLikeByUser.setOnClickListener {
                photo?.let { onLikeClick(it) }
                photoLikeByUser.isSelected = !photoLikeByUser.isSelected
                photo?.likedByUser = photoLikeByUser.isSelected
            }
            Glide
                .with(holder.itemView)
                .load(photo?.urls?.regular)
                .placeholder(R.drawable.placeholder_photo)
                .into(photoView)

            root.setOnClickListener {
                photo?.let {
                    onPhotoClick(it)
                }
            }
        }
    }
}


class PhotoListViewHolder(val binding: PhotoLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) = oldItem == newItem
}
