package com.arnava.photohub.ui.view.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.photo.DetailedPhoto
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.FragmentPhotoDetailsBinding
import com.arnava.photohub.viewmodel.PhotoDetailsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment() {

    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding get() = _binding!!
    private val photoDetailsViewModel: PhotoDetailsViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        arguments?.let {
            it.getString(ARG_PARAM1)?.let { photoId ->
                photoDetailsViewModel.loadPhotoById(photoId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            it.getString(ARG_PARAM1)?.let { photoId ->
                photoDetailsViewModel.loadPhotoById(photoId)
            }
        }
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            photoDetailsViewModel.photo.collect { detailedPhoto ->
                with(binding) {
                    likeByUserDetailed.isSelected = detailedPhoto?.likedByUser ?: false
                    likeByUserDetailed.setOnClickListener { likeIcon ->
                        detailedPhoto?.let { likeClick(it) }
                        likeIcon.isSelected = !likeIcon.isSelected
                        detailedPhoto?.likedByUser = likeIcon.isSelected
                    }
                    Glide
                        .with(this@PhotoDetailsFragment)
                        .load(detailedPhoto?.urls?.full)
                        .placeholder(R.drawable.placeholder_photo)
                        .into(photoView)
                    Glide
                        .with(this@PhotoDetailsFragment)
                        .load(detailedPhoto?.user?.profileImage?.medium)
                        .placeholder(R.drawable.placeholder_photo)
                        .into(authorImage)

                    authorName.text = detailedPhoto?.user?.name
                    authorUrl.text = detailedPhoto?.user?.instagramUsername ?: detailedPhoto?.user?.twitterUsername
                    likesCountDetailed.text = detailedPhoto?.likes.toString()
                    locationText.text = detailedPhoto?.location?.name
                    totalDownloads.text = detailedPhoto?.downloads.toString()
                    likesCountDetailed.text = detailedPhoto?.likes.toString()
                    tagsText.text = detailedPhoto?.tags?.joinToString {
                        " ${it.title}"
                    }
                    exif.text =
                        buildString {
                            append(getString(R.string.camera))
                            append(detailedPhoto?.exif?.name ?: "")
                            append(getString(R.string.iso))
                            append(detailedPhoto?.exif?.iso ?: "")
                            append(getString(R.string.focal))
                            append(detailedPhoto?.exif?.focalLength ?: "")
                            append(getString(R.string.exposure))
                            append(detailedPhoto?.exif?.exposureTime ?: "")
                            append(getString(R.string.aperture))
                            append(detailedPhoto?.exif?.aperture ?: "")
                        }
                    creatorInfo.text = buildString {
                        append(getString(R.string.author))
                        append("\nInstagram: ${detailedPhoto?.user?.instagramUsername ?: ""}")
                        append("\nInfo: ${detailedPhoto?.user?.bio ?: ""}")
                    }

                }
            }
        }

    }

    private fun likeClick(item: DetailedPhoto) {
        runBlocking {
            if (!item.likedByUser) photoDetailsViewModel.likePhoto(item.id)
            else photoDetailsViewModel.unlikePhoto(item.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}