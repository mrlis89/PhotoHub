package com.arnava.photohub.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.photohub.data.models.unsplash.Photo
import com.arnava.photohub.databinding.FragmentHomeBinding
import com.arnava.photohub.ui.paging.PagedPhotoListAdapter
import com.arnava.photohub.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

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
        binding.recyclerView.adapter = pagedPhotoListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.pagingPhotos.collect {
                pagedPhotoListAdapter.submitData(it)
            }
        }
    }

    private fun onPhotoClick(item: Photo) {
//        val bundle = Bundle().apply {
//            putParcelable("param1", item)
//        }
//        findNavController().navigate(R.id.action_mainFragment_to_photoFragment, bundle)
//        parentFragmentManager.commit {
//            replace(R.id.fragmentContainerView, CharacterDetailsFragment::class.java, bundle)
//            addToBackStack(MainFragment::class.java.name)
//        }
    }

    private fun onLikeClick(item: Photo) {
        runBlocking {
            if (!item.likedByUser) homeViewModel.likePhoto(item.id)
            else homeViewModel.unlikePhoto(item.id)
        }
    }

}