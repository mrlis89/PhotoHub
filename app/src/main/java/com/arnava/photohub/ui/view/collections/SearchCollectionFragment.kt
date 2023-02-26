package com.arnava.photohub.ui.view.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.databinding.FragmentCollectionsSearchBinding
import com.arnava.photohub.ui.adapters.FoundCollectionListAdapter
import com.arnava.photohub.viewmodel.SearchCollectionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.arnava.photohub.R

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class SearchCollectionFragment : Fragment() {
    private var param1: String? = null
    private var _binding: FragmentCollectionsSearchBinding? = null
    private val binding get() = _binding!!
    private val searchCollectionsViewModel: SearchCollectionsViewModel by viewModels()
    private val foundCollectionListAdapter = FoundCollectionListAdapter { onCollectionClick(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        param1?.let {
            searchCollectionsViewModel.loadFoundCollections(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSearchCollection.adapter = foundCollectionListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchCollectionsViewModel.pagingCollections.collect {
                foundCollectionListAdapter.setData(it)
            }
        }
    }

    private fun onCollectionClick(collection: FoundCollection) {
        val bundle = Bundle().apply {
            putString(ARG_PARAM1, collection.id.toString())
        }
        findNavController().navigate(R.id.action_searchCollectionFragment_to_collections_photos_fragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, CollectionsPhotosFragment::class.java, bundle)
            addToBackStack(SearchCollectionFragment::class.java.name)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewSearchCollection.adapter = null
        _binding = null
    }

}