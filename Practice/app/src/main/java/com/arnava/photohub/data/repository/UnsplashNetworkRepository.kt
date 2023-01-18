package com.arnava.photohub.data.repository

import com.arnava.photohub.data.models.unsplash.Photo
import com.arnava.photohub.data.api.UnsplashApi
import javax.inject.Inject

class UnsplashNetworkRepository @Inject constructor (private val unsplashApi: UnsplashApi) {

    suspend fun getPhotoList(page: Int = 1): List<Photo> {
       return unsplashApi.getPhotoList(page)
    }

    suspend fun likePhoto(id: String) {
        unsplashApi.sendLike(id)
    }

    suspend fun unlikePhoto(id: String) {
        unsplashApi.deleteLike(id)
    }
}