package com.arnava.photohub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhotoDb::class], version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun getPhotoDao(): PhotoDAO
}