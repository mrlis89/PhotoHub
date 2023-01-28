package com.arnava.photohub.viewmodel

import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnava.photohub.data.models.unsplash.photo.DetailedPhoto
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(private val unsplashNetworkRepository: UnsplashNetworkRepository) : ViewModel() {
    private val _photo = MutableStateFlow<DetailedPhoto?>(null)
    val photo = _photo.asStateFlow()

    fun loadPhotoById(id: String) {
        viewModelScope.launch {
            _photo.value = unsplashNetworkRepository.getDetailedPhoto(id)
        }
    }

    suspend fun likePhoto(id: String) {
        unsplashNetworkRepository.likePhoto(id)
    }

    suspend fun unlikePhoto(id: String) {
        unsplashNetworkRepository.unlikePhoto(id)
    }

}