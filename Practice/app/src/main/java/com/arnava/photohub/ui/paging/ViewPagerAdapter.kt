package com.arnava.photohub.ui.paging

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arnava.photohub.ui.view.photos.LikedPhotoFragment
import com.arnava.photohub.ui.view.profile.ProfileDetailsFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                ProfileDetailsFragment()
            }
            1 -> {
                LikedPhotoFragment()
            }
            else -> {
                ProfileDetailsFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "User info"
            }
            1 -> {
                return "Liked photos"
            }
        }
        return super.getPageTitle(position)
    }

}