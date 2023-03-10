package com.arnava.photohub.data.models.unsplash.photo
import com.arnava.photohub.data.models.unsplash.user.User
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class UnsplashPhoto(
    @Json(name = "id")
    val id: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "color")
    val color: String,
    @Json(name = "blur_hash")
    val blurHash: String,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "liked_by_user")
    var likedByUser: Boolean,
    @Json(name = "description")
    val description: String?,
    @Json(name = "user")
    val user: User?,
    @Json(name = "current_user_collections")
    val currentUserCollections: List<CurrentUserCollection>?,
    @Json(name = "urls")
    val urls: Urls,
    @Json(name = "links")
    val links: LinksX
)

