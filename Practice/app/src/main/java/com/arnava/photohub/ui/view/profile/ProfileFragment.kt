package com.arnava.photohub.ui.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arnava.photohub.R
import com.arnava.photohub.databinding.FragmentProfileBinding
import com.arnava.photohub.ui.paging.ViewPagerAdapter
import com.arnava.photohub.data.local.UserInfoStorage
import com.arnava.photohub.ui.view.logout.LogoutDialogFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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
            Glide
                .with(this@ProfileFragment)
                .load(UserInfoStorage.userInfo?.profileImage?.large)
                .placeholder(R.drawable.placeholder_photo)
                .circleCrop()
                .into(userImageView)

            userFullName.text = buildString {
                append(UserInfoStorage.userInfo?.firstName)
                append(" ")
                append(UserInfoStorage.userInfo?.lastName)
            }
            userUrl.text = UserInfoStorage.userInfo?.instagramUsername
            val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
            viewPager.adapter = viewPagerAdapter
            viewPagerAdapter.notifyDataSetChanged()
            viewPagerTab.setupWithViewPager(viewPager)
            logoutBtn.setOnClickListener {
                LogoutDialogFragment().show(parentFragmentManager,"dialog")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}