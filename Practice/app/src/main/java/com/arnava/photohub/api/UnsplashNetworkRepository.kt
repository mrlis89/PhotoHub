package com.arnava.photohub.api

import com.arnava.photohub.api.model.Photo
import javax.inject.Inject

class UnsplashNetworkRepository @Inject constructor (private val characterListApi: UnsplashApi) {

    suspend fun getPhotoList(page: Int = 1): List<Photo> {
       return characterListApi.getPhotoList(page)
    }
}