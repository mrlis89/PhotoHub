package com.arnava.photohub.di

import android.content.Context
import androidx.room.Room
import com.arnava.photohub.data.db.AppDB
import com.arnava.photohub.data.db.PhotoDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule{
    @Singleton
    @Provides
    fun providesPhotoDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, AppDB::class.java, "photosDB")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesPhotoDAO(appDB: AppDB): PhotoDAO {
        return appDB.getPhotoDao()
    }
}