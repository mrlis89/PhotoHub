package com.arnava.photohub.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.photohub.data.db.PhotoDb
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.repository.DbRepository
import com.arnava.photohub.data.repository.UnsplashNetworkRepository

class PhotoPagingSource(
    private val repository: UnsplashNetworkRepository,
    private val dbRepository: DbRepository,
) :
    PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getPhotoList(page)
        }.fold(
            onSuccess = {
                it.forEach { unsplashPhoto ->
                    dbRepository.addPhoto(
                        PhotoDb(
                            unsplashPhoto.urls.regular,
                            unsplashPhoto.likes,
                            unsplashPhoto.likedByUser
                        )
                    )
                }
                LoadResult.Page(
                    it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1

                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }

}