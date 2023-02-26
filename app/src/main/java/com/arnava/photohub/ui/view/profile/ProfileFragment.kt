package com.arnava.photohub.ui.view.profile

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
import com.arnava.photohub.databinding.FragmentProfileBinding
import com.arnava.photohub.data.local.UserInfoStorage
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.ui.adapters.PagedPhotoListAdapter
import com.arnava.photohub.ui.view.logout.LogoutDialogFragment
import com.arnava.photohub.ui.view.photos.PhotoDetailsFragment
import com.arnava.photohub.viewmodel.LikedPhotosViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LikedPhotosViewModel by viewModels()
    private val pagedPhotoListAdapter = PagedPhotoListAdapter(
        { onPhotoClick(it) },
        { onLikeClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            } else {
                recyclerView.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            }
            recyclerView.adapter = pagedPhotoListAdapter

            Glide
                .with(this@ProfileFragment)
                .load(UserInfoStorage.userInfo?.profileImage?.large)
                .placeholder(R.drawable.placeholder_photo)
                .circleCrop()
                .into(userImageView)

            userBio.text = UserInfoStorage.userInfo?.bio
            userLocation.text = UserInfoStorage.userInfo?.location.toString()
            userEmail.text = UserInfoStorage.userInfo?.email
            userFullName.text = buildString {
                append(UserInfoStorage.userInfo?.firstName)
                append(" ")
                append(UserInfoStorage.userInfo?.lastName)
            }
            userUrl.text = UserInfoStorage.userInfo?.instagramUsername


            logoutBtn.setOnClickListener {
                LogoutDialogFragment().show(parentFragmentManager,"dialog")
            }


            refreshBtn.setOnClickListener {
                pagedPhotoListAdapter.refresh()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagingPhotos.collect {
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
    }

    private fun onPhotoClick(item: UnsplashPhoto) {
        val bundle = Bundle().apply {
            putString(ARG_PARAM1, item.id)
        }
        findNavController().navigate(R.id.action_navigation_profile_to_photoDetailsFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, PhotoDetailsFragment::class.java, bundle)
            addToBackStack(ProfileFragment::class.java.name)
        }
    }

    private fun onLikeClick(item: UnsplashPhoto) {
        runBlocking {
            if (!item.likedByUser) viewModel.likePhoto(item.id)
            else viewModel.unlikePhoto(item.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}