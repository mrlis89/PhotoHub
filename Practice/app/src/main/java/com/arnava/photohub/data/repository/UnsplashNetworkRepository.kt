package com.arnava.photohub.data.repository

import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.api.UnsplashApi
import javax.inject.Inject

class UnsplashNetworkRepository @Inject constructor (private val unsplashApi: UnsplashApi) {

    suspend fun getPhotoList(page: Int = 1): List<UnsplashPhoto> {
       return unsplashApi.getPhotoList(page)
    }

    suspend fun getFoundPhotoList(page: Int = 1, query: String): List<UnsplashPhoto> {
       return unsplashApi.searchPhotos(page, query).results
    }

    suspend fun likePhoto(id: String) {
        unsplashApi.sendLike(id)
    }

    suspend fun unlikePhoto(id: String) {
        unsplashApi.deleteLike(id)
    }
}