package com.arnava.photohub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import com.arnava.photohub.ui.paging.CollectionPagingSource
import com.arnava.photohub.ui.paging.PhotoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class CollectionsViewModel @Inject constructor(private val unsplashNetworkRepository: UnsplashNetworkRepository) : ViewModel() {
    var pagingPhotos: Flow<PagingData<PhotoCollection>> = Pager(
        PagingConfig(PAGE_SIZE),
        null,
    ) {
        CollectionPagingSource(unsplashNetworkRepository)
    }
        .flow
        .cachedIn(viewModelScope)

}