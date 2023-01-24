package com.arnava.photohub.ui.view.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.FragmentSearchBinding
import com.arnava.photohub.ui.adapters.PagedPhotoListAdapter
import com.arnava.photohub.ui.view.photos.PhotoDetailsFragment
import com.arnava.photohub.viewmodel.CollectionsPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class CollectionsPhotosFragment : Fragment() {
    private var param1: String? = null
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val collectionsPhotosViewModel: CollectionsPhotosViewModel by viewModels()
    private val pagedPhotoListAdapter = PagedPhotoListAdapter(
        { onCollectionClick(it) },
        { onLikeClick(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        param1?.let {
            collectionsPhotosViewModel.loadCollectionsPhotos(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedPhotoListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            collectionsPhotosViewModel.pagingPhotos.collect {
                pagedPhotoListAdapter.submitData(it)
            }
        }
    }

    private fun onCollectionClick(item: UnsplashPhoto) {
        val bundle = Bundle().apply {
            putString(ARG_PARAM1, item.id)
        }
        findNavController().navigate(R.id.action_collections_photos_fragment_to_photoDetailsFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, PhotoDetailsFragment::class.java, bundle)
            addToBackStack(CollectionsPhotosFragment::class.java.name)
        }
    }

    private fun onLikeClick(item: UnsplashPhoto) {
        runBlocking {
            if (!item.likedByUser) collectionsPhotosViewModel.likePhoto(item.id)
            else collectionsPhotosViewModel.unlikePhoto(item.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

}