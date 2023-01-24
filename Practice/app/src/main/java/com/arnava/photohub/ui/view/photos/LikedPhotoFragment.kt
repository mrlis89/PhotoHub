package com.arnava.photohub.ui.view.photos

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
import com.arnava.photohub.databinding.FragmentLikedPhotosBinding
import com.arnava.photohub.databinding.FragmentSearchBinding
import com.arnava.photohub.ui.adapters.PagedPhotoListAdapter
import com.arnava.photohub.ui.view.profile.ProfileFragment
import com.arnava.photohub.viewmodel.LikedPhotosViewModel
import com.arnava.photohub.viewmodel.SearchPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class LikedPhotoFragment : Fragment() {
    private var _binding: FragmentLikedPhotosBinding? = null
    private val binding get() = _binding!!
    private val likedPhotosViewModel: LikedPhotosViewModel by viewModels()
    private val pagedPhotoListAdapter = PagedPhotoListAdapter(
        { onPhotoClick(it) },
        { onLikeClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikedPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedPhotoListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            likedPhotosViewModel.pagingPhotos.collect {
                pagedPhotoListAdapter.submitData(it)
            }
        }
    }

    private fun onPhotoClick(item: UnsplashPhoto) {
//        val bundle = Bundle().apply {
//            putString(ARG_PARAM1, item.id)
//        }
//        findNavController().navigate(R.id.action_navigation_profile_to_photoDetailsFragment, bundle)
//        parentFragmentManager.commit {
//            replace(R.id.nav_host_fragment, PhotoDetailsFragment::class.java, bundle)
//            addToBackStack(ProfileFragment::class.java.name)
//        }
    }

    private fun onLikeClick(item: UnsplashPhoto) {
        runBlocking {
            if (!item.likedByUser) likedPhotosViewModel.likePhoto(item.id)
            else likedPhotosViewModel.unlikePhoto(item.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

}