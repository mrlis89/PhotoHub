package com.arnava.photohub.ui.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.photohub.App
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.PhotoLayoutBinding
import com.arnava.photohub.utils.common.NetworkState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class PagedPhotoListAdapter (
    private val onPhotoClick: (UnsplashPhoto) -> Unit,
    private val onLikeClick: (UnsplashPhoto) -> Unit,
) : PagingDataAdapter<UnsplashPhoto, PhotoListViewHolder>(PhotoDiffUtilCallback()) {

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
                .apply(
                    RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .placeholder(R.drawable.placeholder_photo)
                .into(photoView)

            root.setOnClickListener {
                if (NetworkState.isConnected()) {
                    photo?.let {
                        onPhotoClick(it)
                    }
                } else NetworkState.connectionLostToast()
            }
        }
    }
}


class PhotoListViewHolder(val binding: PhotoLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class PhotoDiffUtilCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) = oldItem == newItem
}
