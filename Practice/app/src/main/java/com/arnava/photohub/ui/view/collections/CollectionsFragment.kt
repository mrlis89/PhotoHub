package com.arnava.photohub.ui.view.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.databinding.FragmentCollectionsBinding
import com.arnava.photohub.ui.adapters.PagedCollectionListAdapter
import com.arnava.photohub.ui.view.home.HomeFragment
import com.arnava.photohub.viewmodel.CollectionsViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!
    private val collectionsViewModel: CollectionsViewModel by viewModels()
    private val pagedCollectionListAdapter = PagedCollectionListAdapter { onCollectionClick(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewCollection.adapter = pagedCollectionListAdapter
        binding.searchCollection.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    findNavController().navigate(R.id.action_navigation_collections_to_searchCollectionFragment)
                    val bundle = Bundle().apply {
                        putString(ARG_PARAM1, query)
                    }
                    parentFragmentManager.commit {
                        replace(R.id.nav_host_fragment, SearchCollectionFragment::class.java, bundle)
                        addToBackStack(CollectionsFragment::class.java.name)
                    }
                }
                return false
            }
        })
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            collectionsViewModel.pagingPhotos.collect {
                pagedCollectionListAdapter.submitData(it)
            }
        }
    }

    private fun onCollectionClick(item: PhotoCollection) {
        val bundle = Bundle().apply {
            putString(ARG_PARAM1, item.id)
        }
        findNavController().navigate(R.id.action_navigation_collections_to_collections_photos_fragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, CollectionsPhotosFragment::class.java, bundle)
            addToBackStack(HomeFragment::class.java.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewCollection.adapter = null
        _binding = null
    }
}