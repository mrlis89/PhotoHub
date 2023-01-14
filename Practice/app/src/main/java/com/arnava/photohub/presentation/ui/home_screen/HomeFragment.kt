package com.arnava.photohub.presentation.ui.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.arnava.photohub.api.model.Photo
import com.arnava.photohub.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.items
import com.arnava.photohub.presentation.ui.composeViews.PhotoView
import com.arnava.rick_n_morty.ui.theme.UnsplashTheme

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            UnsplashTheme {
                ListView()
            }
        }
    }

    @Composable
    fun ListView() {
        val lazyPagingItems: LazyPagingItems<Photo> =
            homeViewModel.pagingPhotos.collectAsLazyPagingItems()

        LazyColumn {
            items(lazyPagingItems) {
                it?.let {
                    PhotoView(it) { onItemClick(it) }
                } ?: Text(text = "something went wrong")
            }

            lazyPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = lazyPagingItems.loadState.refresh as LoadState.Error
                        item {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                                horizontalAlignment = CenterHorizontally
                            )
                            {
                                e.error.localizedMessage?.let { Text(text = it) }
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }

                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = lazyPagingItems.loadState.append as LoadState.Error
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                horizontalAlignment = CenterHorizontally
                            ) {
                                e.error.localizedMessage?.let { Text(text = it) }
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onItemClick(item: Photo) {
//        val bundle = Bundle().apply {
//            putParcelable("param1", item)
//        }
//        findNavController().navigate(R.id.action_mainFragment_to_photoFragment, bundle)
//        parentFragmentManager.commit {
//            replace(R.id.fragmentContainerView, CharacterDetailsFragment::class.java, bundle)
//            addToBackStack(MainFragment::class.java.name)
//        }
    }

}