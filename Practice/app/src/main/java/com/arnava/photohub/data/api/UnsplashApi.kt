package com.arnava.photohub.data.api

import com.arnava.photohub.data.models.unsplash.collection.FoundCollectionList
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.data.models.unsplash.photo.DetailedPhoto
import com.arnava.photohub.data.models.unsplash.photo.FoundPhotoList
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.models.unsplash.user.UserInfo
import retrofit2.http.*

interface UnsplashApi {
    @GET("photos")
    suspend fun getPhotoList(
        @Query("page") page: Int,
        @Query("order_by") orderBy: String = "popular",
    ): List<UnsplashPhoto>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): FoundPhotoList

    @GET("photos/{id}")
    suspend fun getPhotoDetailInfo(
        @Path("id") id: String
    ): DetailedPhoto

    @GET("search/collections")
    suspend fun searchCollections(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): FoundCollectionList

    @GET("users/{username}/likes")
    suspend fun getUserLikedPhotos(
        @Path("username") userName: String,
        @Query("page") page: Int,
    ): List<UnsplashPhoto>

    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("page") page: Int,
    ): List<UnsplashPhoto>

    @GET("collections")
    suspend fun getListCollections(
        @Query("page") page: Int,
    ): List<PhotoCollection>

    @GET("me")
    suspend fun getUserInfo(): UserInfo

    @POST("photos/{id}/like")
    suspend fun sendLike(
        @Path("id") id: String
    )

    @DELETE("photos/{id}/like")
    suspend fun deleteLike(
        @Path("id") id: String
    )

}
