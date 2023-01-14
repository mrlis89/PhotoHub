package com.arnava.photohub.api

import com.arnava.photohub.api.model.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    suspend fun getPhotoList(@Query("page") page: Int): List<Photo>
}