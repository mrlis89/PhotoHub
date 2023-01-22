package com.arnava.photohub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.databinding.CollectionLayoutBinding
import com.bumptech.glide.Glide

class PagedCollectionListAdapter (
    private val onCollectionClick: (PhotoCollection) -> Unit,
) : PagingDataAdapter<PhotoCollection, CollectionListViewHolder>(CollectionDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionListViewHolder {
        val binding = CollectionLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return CollectionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionListViewHolder, position: Int) {
        val collection = getItem(position)
        with(holder.binding) {
            collectionPhotoCount.text = collection?.totalPhotos.toString()
            collectionLabel.text = collection?.title
            Glide
                .with(holder.itemView)
                .load(collection?.coverPhoto?.urls?.regular)
                .placeholder(R.drawable.placeholder_photo)
                .into(collectionView)

            root.setOnClickListener {
                collection?.let {
                    onCollectionClick(it)
                }
            }
        }
    }
}


class CollectionListViewHolder(val binding: CollectionLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class CollectionDiffUtilCallback : DiffUtil.ItemCallback<PhotoCollection>() {
    override fun areItemsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection) = oldItem == newItem
}
