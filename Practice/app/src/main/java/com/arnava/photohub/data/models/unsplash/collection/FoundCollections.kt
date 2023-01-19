package com.arnava.photohub.data.models.unsplash.collection
import com.arnava.photohub.data.models.unsplash.photo.Links
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.models.unsplash.user.ProfileImage
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class FoundCollectionList(
    @Json(name = "total")
    val total: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "results")
    val results: List<Result>
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: Any?,
    @Json(name = "published_at")
    val publishedAt: String,
    @Json(name = "last_collected_at")
    val lastCollectedAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "featured")
    val featured: Boolean,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "private")
    val privateCollectionList: Boolean,
    @Json(name = "share_key")
    val shareKey: String,
    @Json(name = "cover_photo")
    val coverPhoto: UnsplashPhoto,
    @Json(name = "user")
    val user: UserX,
    @Json(name = "links")
    val links: LinksRelated
)

@JsonClass(generateAdapter = true)
data class UserX(
    @Json(name = "id")
    val id: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "portfolio_url")
    val portfolioUrl: Any?,
    @Json(name = "bio")
    val bio: String,
    @Json(name = "profile_image")
    val profileImage: ProfileImage,
    @Json(name = "links")
    val links: LinksRelated
)

@JsonClass(generateAdapter = true)
data class LinksRelated(
    @Json(name = "self")
    val self: String,
    @Json(name = "html")
    val html: String,
    @Json(name = "photos")
    val photos: String,
    @Json(name = "related")
    val related: String
)

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val id: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "instagram_username")
    val instagramUsername: String,
    @Json(name = "twitter_username")
    val twitterUsername: String,
    @Json(name = "portfolio_url")
    val portfolioUrl: String,
    @Json(name = "profile_image")
    val profileImage: ProfileImage,
    @Json(name = "links")
    val links: Links
)



