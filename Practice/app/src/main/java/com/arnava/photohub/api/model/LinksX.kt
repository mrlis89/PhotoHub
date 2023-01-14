package com.arnava.photohub.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinksX(
    @Json(name = "self")
    val self: String,
    @Json(name = "html")
    val html: String,
    @Json(name = "download")
    val download: String,
    @Json(name = "download_location")
    val downloadLocation: String
)