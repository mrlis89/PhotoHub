package com.arnava.photohub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.databinding.CollectionLayoutBinding
import com.bumptech.glide.Glide

class FoundCollectionListAdapter(
    private val onCollectionClick: (FoundCollection) -> Unit
) :
    RecyclerView.Adapter<ColListViewHolder>() {

    private var data: List<FoundCollection?> = emptyList()
    fun setData(data: List<FoundCollection?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColListViewHolder {
        val binding = CollectionLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ColListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColListViewHolder, position: Int) {
        val collection = data[position]
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

    override fun getItemCount(): Int {
        return data.size
    }
}

class ColListViewHolder(val binding: CollectionLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)