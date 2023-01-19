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
import kotlinx.coroutines.flow.asFlow
import retrofit2.http.Query
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class SearchPhotosViewModel @Inject constructor(private val repository: UnsplashNetworkRepository) :
    ViewModel() {
    var pagingPhotos: Flow<PagingData<UnsplashPhoto>> = emptyList<PagingData<UnsplashPhoto>>().asFlow()

    fun loadFoundPhotos(query: String) {
        pagingPhotos = Pager(
            PagingConfig(PAGE_SIZE),
            null,
        ) {
            SearchPhotoPagingSource(repository, query)
        }
            .flow
            .cachedIn(viewModelScope)
    }

    suspend fun likePhoto(id: String) {
        repository.likePhoto(id)
    }

    suspend fun unlikePhoto(id: String) {
        repository.unlikePhoto(id)
    }

}