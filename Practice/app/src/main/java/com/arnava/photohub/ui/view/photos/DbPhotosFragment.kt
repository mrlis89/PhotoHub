package com.arnava.photohub.ui.view.photos

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.FragmentDbPhotosBinding
import com.arnava.photohub.databinding.FragmentLikedPhotosBinding
import com.arnava.photohub.ui.adapters.PhotoDbListAdapter
import com.arnava.photohub.viewmodel.LikedPhotosViewModel
import com.arnava.photohub.viewmodel.PhotoDbViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class DbPhotosFragment : Fragment() {
    private var _binding: FragmentDbPhotosBinding? = null
    private val binding get() = _binding!!
    private val photoDbViewModel: PhotoDbViewModel by viewModels()
    private val photoDbListAdapter = PhotoDbListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDbPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        } else {
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.recyclerView.adapter = photoDbListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            photoDbViewModel.photos.collect {
                photoDbListAdapter.setData(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

}