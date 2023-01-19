package com.arnava.photohub.data.models.unsplash.collection

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionTag(
    @Json(name = "type")
    val type : String,
    @Json(name = "title")
    val title : String,
)
