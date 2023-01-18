package com.arnava.photohub.data.api

import com.arnava.photohub.data.models.unsplash.Photo
import retrofit2.http.*

interface UnsplashApi {
    @GET("photos")
    suspend fun getPhotoList(
        @Query("page") page: Int,
        @Query("order_by") orderBy: String = "popular",
    ): List<Photo>

    @POST("photos/{id}/like")
    suspend fun sendLike(
        @Path("id") id: String
    )

    @DELETE("photos/{id}/like")
    suspend fun deleteLike(
        @Path("id") id: String
    )
}