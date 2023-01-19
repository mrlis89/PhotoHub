package com.arnava.photohub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import com.arnava.photohub.ui.paging.PhotoPagingSource
import com.arnava.photohub.ui.paging.SearchPhotoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UnsplashNetworkRepository) :
    ViewModel() {
    var pagingPhotos: Flow<PagingData<UnsplashPhoto>> = Pager(
        PagingConfig(PAGE_SIZE),
        null,
    ) {
        PhotoPagingSource(repository)
    }
        .flow
        .cachedIn(viewModelScope)

    suspend fun likePhoto(id: String) {
        repository.likePhoto(id)
    }

    suspend fun unlikePhoto(id: String) {
        repository.unlikePhoto(id)
    }

}