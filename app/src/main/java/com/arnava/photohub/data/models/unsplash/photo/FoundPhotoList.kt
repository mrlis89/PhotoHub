package com.arnava.photohub.data.models.unsplash.photo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoundPhotoList(
    @Json(name = "total")
    val total: Long,
    @Json(name = "total_pages")
    val totalPages: Long,
    @Json(name = "results")
    val results: List<UnsplashPhoto>
)
