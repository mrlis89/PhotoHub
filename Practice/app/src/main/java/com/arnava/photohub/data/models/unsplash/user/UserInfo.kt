package com.arnava.photohub.data.models.unsplash.user

import com.arnava.photohub.data.models.unsplash.photo.Links
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "id")
    val id: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "twitter_username")
    val twitterUsername: String?,
    @Json(name = "portfolio_url")
    val portfolioUrl: Any?,
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "location")
    val location: Any?,
    @Json(name = "total_likes")
    val totalLikes: Int,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "total_collections")
    val totalCollections: Int,
    @Json(name = "followed_by_user")
    val followedByUser: Boolean,
    @Json(name = "downloads")
    val downloads: Int,
    @Json(name = "uploads_remaining")
    val uploadsRemaining: Int,
    @Json(name = "instagram_username")
    val instagramUsername: String?,
    @Json(name = "email")
    val email: String,
    @Json(name = "links")
    val links: Links,
    @Json(name = "profile_image")
    val profileImage: ProfileImage
)

