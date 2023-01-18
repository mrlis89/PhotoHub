package com.arnava.photohub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import com.arnava.photohub.data.models.unsplash.Photo
import com.arnava.photohub.ui.paging.PhotoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UnsplashNetworkRepository) : ViewModel() {
    val pagingPhotos: Flow<PagingData<Photo>> = Pager(
        PagingConfig(PAGE_SIZE),
        null,
    ) { PhotoPagingSource(repository) }.flow.cachedIn(viewModelScope)

    suspend fun likePhoto(id: String) {
        repository.likePhoto(id)
    }

    suspend fun unlikePhoto(id: String) {
        repository.unlikePhoto(id)
    }

}