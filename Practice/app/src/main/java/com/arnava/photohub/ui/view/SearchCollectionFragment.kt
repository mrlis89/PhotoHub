package com.arnava.photohub.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.databinding.FragmentCollectionsSearchBinding
import com.arnava.photohub.ui.adapters.PagedFoundCollectionListAdapter
import com.arnava.photohub.viewmodel.SearchCollectionsViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class SearchCollectionFragment : Fragment() {
    private var param1: String? = null
    private var _binding: FragmentCollectionsSearchBinding? = null
    private val binding get() = _binding!!
    private val searchCollectionsViewModel: SearchCollectionsViewModel by viewModels()
    private val pagedFoundCollectionListAdapter = PagedFoundCollectionListAdapter { onCollectionClick(it) }

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
        binding.recyclerViewSearchCollection.adapter = pagedFoundCollectionListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchCollectionsViewModel.pagingCollections.collect {
                pagedFoundCollectionListAdapter.submitData(it)
            }
        }
    }

    private fun onCollectionClick(collection: FoundCollection) {
//        val bundle = Bundle().apply {
//            putString(ARG_PARAM1, collection.id.toString())
//        }
//        findNavController().navigate(R.id.action_navigation_collections_to_searchCollectionFragment, bundle)
//        parentFragmentManager.commit {
//            replace(R.id.nav_host_fragment, SearchCollectionFragment::class.java, bundle)
//            addToBackStack(HomeFragment::class.java.name)
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewSearchCollection.adapter = null
        _binding = null
    }

}