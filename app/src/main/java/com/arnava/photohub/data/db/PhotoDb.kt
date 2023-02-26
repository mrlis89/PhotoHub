package com.arnava.photohub.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "photoTable")
data class PhotoDb(
    @PrimaryKey
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "photoLikesCount")
    val photoLikesCount: Int,
    @ColumnInfo(name = "photoLikeByUser")
    val photoLikeByUser: Boolean
)
