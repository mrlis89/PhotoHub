package com.arnava.photohub.ui.view.photos

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.FragmentSearchBinding
import com.arnava.photohub.ui.adapters.PagedPhotoListAdapter
import com.arnava.photohub.ui.view.home.HomeFragment
import com.arnava.photohub.viewmodel.SearchPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class SearchPhotoFragment : Fragment() {
    private var param1: String? = null
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchPhotosViewModel: SearchPhotosViewModel by viewModels()
    private val pagedPhotoListAdapter = PagedPhotoListAdapter(
        { onPhotoClick(it) },
        { onLikeClick(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        param1?.let {
            searchPhotosViewModel.loadFoundPhotos(it)
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
        if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        } else {
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.recyclerView.adapter = pagedPhotoListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchPhotosViewModel.pagingPhotos.collect {
                pagedPhotoListAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            pagedPhotoListAdapter.loadStateFlow.collectLatest {
                binding.progressBtn.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Loading) binding.refreshLayout.isVisible = false
                if (it.refresh is LoadState.Error || it.append is LoadState.Error) binding.refreshLayout.isVisible = true
            }
        }

        binding.refreshBtn.setOnClickListener {
            pagedPhotoListAdapter.refresh()
        }
    }

    private fun onPhotoClick(item: UnsplashPhoto) {
        val bundle = Bundle().apply {
            putString(ARG_PARAM1, item.id)
        }
        findNavController().navigate(R.id.action_navigation_search_photo_fragment_to_photoDetailsFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, PhotoDetailsFragment::class.java, bundle)
            addToBackStack(HomeFragment::class.java.name)
        }
    }

    private fun onLikeClick(item: UnsplashPhoto) {
        runBlocking {
            if (!item.likedByUser) searchPhotosViewModel.likePhoto(item.id)
            else searchPhotosViewModel.unlikePhoto(item.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

}