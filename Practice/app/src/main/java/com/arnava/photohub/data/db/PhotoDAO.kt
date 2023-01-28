package com.arnava.photohub.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDAO {

    @Query("SELECT * FROM photoTable")
    fun getAll(): Flow<List<PhotoDb>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photoDb: PhotoDb): Long
}