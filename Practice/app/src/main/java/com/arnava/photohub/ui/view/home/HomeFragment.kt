package com.arnava.photohub.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.FragmentHomeBinding
import com.arnava.photohub.ui.adapters.PagedPhotoListAdapter
import com.arnava.photohub.ui.view.photos.PhotoDetailsFragment
import com.arnava.photohub.ui.view.photos.SearchPhotoFragment
import com.arnava.photohub.data.local.UserInfoStorage
import com.arnava.photohub.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
private const val ARG_PARAM1 = "param1"
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val pagedPhotoListAdapter = PagedPhotoListAdapter(
        { onPhotoClick(it) },
        { onLikeClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = UserInfoStorage.userInfo
        binding.recyclerView.adapter = pagedPhotoListAdapter
        binding.searchPhoto.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()){
                    findNavController().navigate(R.id.action_navigation_home_to_navigation_search_photo_fragment)
                    val bundle = Bundle().apply {
                        putString(ARG_PARAM1, query)
                    }
                    parentFragmentManager.commit {
                        replace(R.id.nav_host_fragment, SearchPhotoFragment::class.java, bundle)
                        addToBackStack(HomeFragment::class.java.name)
                    }
                }
                return false
            }
        })
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.pagingPhotos.collect {
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
        findNavController().navigate(R.id.action_navigation_home_to_photoDetailsFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, PhotoDetailsFragment::class.java, bundle)
            addToBackStack(HomeFragment::class.java.name)
        }
    }

    private fun onLikeClick(item: UnsplashPhoto) {
        runBlocking {
            if (!item.likedByUser) homeViewModel.likePhoto(item.id)
            else homeViewModel.unlikePhoto(item.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

}