package com.arnava.photohub.data.api

import com.arnava.photohub.data.models.dto.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    suspend fun getPhotoList(@Query("page") page: Int): List<Photo>
}