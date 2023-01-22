package com.arnava.photohub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.databinding.CollectionLayoutBinding
import com.arnava.photohub.databinding.FragmentCollectionsSearchBinding
import com.bumptech.glide.Glide

class PagedFoundCollectionListAdapter (
    private val onCollectionClick: (FoundCollection) -> Unit,
) : PagingDataAdapter<FoundCollection, FoundCollectionListViewHolder>(FoundCollectionDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundCollectionListViewHolder {
        val binding = CollectionLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return FoundCollectionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoundCollectionListViewHolder, position: Int) {
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


class FoundCollectionListViewHolder(val binding: CollectionLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class FoundCollectionDiffUtilCallback : DiffUtil.ItemCallback<FoundCollection>() {
    override fun areItemsTheSame(oldItem: FoundCollection, newItem: FoundCollection) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FoundCollection, newItem: FoundCollection) = oldItem == newItem
}
