package com.arnava.photohub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import com.arnava.photohub.ui.paging.PhotoPagingSource
import com.arnava.photohub.ui.paging.SearchCollectionPagingSource
import com.arnava.photohub.ui.paging.SearchPhotoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.http.Query
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class SearchCollectionsViewModel @Inject constructor(private val repository: UnsplashNetworkRepository) :
    ViewModel() {
    var pagingCollections: Flow<PagingData<FoundCollection>> = emptyList<PagingData<FoundCollection>>().asFlow()

    fun loadFoundCollections(query: String) {
        pagingCollections = Pager(
            PagingConfig(PAGE_SIZE),
            null,
        ) {
            SearchCollectionPagingSource(repository, query)
        }
            .flow
            .cachedIn(viewModelScope)
    }

}