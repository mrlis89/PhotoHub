package com.arnava.photohub.data.repository

import com.arnava.photohub.data.models.dto.Photo
import com.arnava.photohub.data.api.UnsplashApi
import javax.inject.Inject

class UnsplashNetworkRepository @Inject constructor (private val characterListApi: UnsplashApi) {

    suspend fun getPhotoList(page: Int = 1): List<Photo> {
       return characterListApi.getPhotoList(page)
    }
}