package com.arnava.photohub.data.models.unsplash.collection

import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.models.unsplash.user.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoCollection(
    @Json(name = "id")
    val id : String,
    @Json(name = "title")
    val title : String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "total_photos")
    val totalPhotos: Long,
    @Json(name = "cover_photo")
    val coverPhoto: UnsplashPhoto,
    @Json(name = "user")
    val user: User,
    val tags: List<CollectionTag>,
)

