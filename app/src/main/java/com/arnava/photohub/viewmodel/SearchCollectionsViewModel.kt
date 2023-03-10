package com.arnava.photohub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class SearchCollectionsViewModel @Inject constructor(private val repository: UnsplashNetworkRepository) :
    ViewModel() {

    private val _pagingCollections = MutableStateFlow<List<FoundCollection?>>(emptyList())
    val pagingCollections = _pagingCollections.asStateFlow()

    fun loadFoundCollections(query: String) {
        viewModelScope.launch {
            _pagingCollections.value = repository.getFoundCollectionList(1, query)
        }

    }
}