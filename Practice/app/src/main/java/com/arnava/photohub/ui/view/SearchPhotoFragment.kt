package com.arnava.photohub.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.databinding.FragmentHomeBinding
import com.arnava.photohub.ui.paging.PagedPhotoListAdapter
import com.arnava.photohub.viewmodel.SearchPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class SearchPhotoFragment : Fragment() {
    private var param1: String? = null
    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedPhotoListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchPhotosViewModel.pagingPhotos.collect {
                pagedPhotoListAdapter.submitData(it)
            }
        }
    }

    private fun onPhotoClick(item: UnsplashPhoto) {
//        val bundle = Bundle().apply {
//            putParcelable("param1", item)
//        }
//        findNavController().navigate(R.id.action_mainFragment_to_photoFragment, bundle)
//        parentFragmentManager.commit {
//            replace(R.id.fragmentContainerView, CharacterDetailsFragment::class.java, bundle)
//            addToBackStack(MainFragment::class.java.name)
//        }
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