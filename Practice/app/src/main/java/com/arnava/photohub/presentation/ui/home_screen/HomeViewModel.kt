package com.arnava.photohub.presentation.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arnava.photohub.api.UnsplashNetworkRepository
import com.arnava.photohub.api.model.Photo
import com.arnava.photohub.presentation.PhotoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UnsplashNetworkRepository) :
    ViewModel() {
    val pagingPhotos: Flow<PagingData<Photo>> = Pager(
        PagingConfig(PAGE_SIZE),
        null,
    ) { PhotoPagingSource(repository) }.flow
}