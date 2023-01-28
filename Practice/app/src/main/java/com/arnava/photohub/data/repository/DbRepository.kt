package com.arnava.photohub.data.repository

import com.arnava.photohub.data.db.PhotoDAO
import com.arnava.photohub.data.db.PhotoDb
import javax.inject.Inject

class DbRepository @Inject constructor (private val photoDAO: PhotoDAO) {
    fun getPhotos() = photoDAO.getAll()
    suspend fun addPhoto(photoDb: PhotoDb) = photoDAO.insert(photoDb)
}