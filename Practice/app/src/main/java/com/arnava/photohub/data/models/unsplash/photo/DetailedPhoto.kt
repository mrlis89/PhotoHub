package com.arnava.photohub.data.models.unsplash.photo

import com.arnava.photohub.data.models.unsplash.collection.CollectionTag
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.data.models.unsplash.common.Location
import com.arnava.photohub.data.models.unsplash.user.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DetailedPhoto(
    @Json(name = "id")
    val id : String,
    @Json(name = "created_at")
    val createdAt : String,
    @Json(name = "updated_at")
    val updatedAt : String,
    @Json(name = "width")
    val width : Int,
    @Json(name = "height")
    val height : Int,
    @Json(name = "color")
    val color : String,
    @Json(name = "blur_hash")
    val blurHash : String,
    @Json(name = "downloads")
    val downloads : Long,
    @Json(name = "likes")
    val likes : Long,
    @Json(name = "liked_by_user")
    var likedByUser: Boolean,
    @Json(name = "public_domain")
    val publicDomain: Boolean,
    @Json(name = "description")
    val description: String?,
    @Json(name = "exif")
    val exif: Exif?,
    @Json(name = "location")
    val location: Location?,
    @Json(name = "tags")
    val tags: List<CollectionTag>?,
    @Json(name = "current_user_collections")
    val currentUserCollection: List<PhotoCollection>?,
    @Json(name = "urls")
    val urls: Urls,
    @Json(name = "links")
    val links: Links,
    @Json(name = "user")
    val user: User
)