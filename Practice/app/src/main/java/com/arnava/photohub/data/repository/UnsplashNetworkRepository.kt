package com.arnava.photohub.data.repository

import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.api.UnsplashApi
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.data.models.unsplash.photo.DetailedPhoto
import javax.inject.Inject

class UnsplashNetworkRepository @Inject constructor (private val unsplashApi: UnsplashApi) {

    suspend fun getPhotoList(page: Int = 1): List<UnsplashPhoto> {
       return unsplashApi.getPhotoList(page)
    }

    suspend fun getCollectionsPhotos(id:String, page: Int = 1): List<UnsplashPhoto> {
       return unsplashApi.getCollectionPhotos(id, page)
    }

    suspend fun getUserLikedPhotos(userId:String, page: Int = 1): List<UnsplashPhoto> {
       return unsplashApi.getUserLikedPhotos(userId, page)
    }

    suspend fun getCollectionList(page: Int = 1): List<PhotoCollection> {
       return unsplashApi.getCollectionList(page)
    }

    suspend fun getFoundPhotoList(page: Int = 1, query: String): List<UnsplashPhoto> {
       return unsplashApi.searchPhotos(page, query).results
    }

    suspend fun getFoundCollectionList(page: Int = 1, query: String): List<FoundCollection> {
       return unsplashApi.searchCollections(page, query).results
    }

    suspend fun likePhoto(id: String) {
        unsplashApi.sendLike(id)
    }

    suspend fun unlikePhoto(id: String) {
        unsplashApi.deleteLike(id)
    }

    suspend fun getDetailedPhoto(id: String): DetailedPhoto {
        return unsplashApi.getPhotoDetailInfo(id)
    }

    suspend fun getUserInfo() = unsplashApi.getUserInfo()
}