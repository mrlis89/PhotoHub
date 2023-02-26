package com.arnava.photohub.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import com.arnava.photohub.data.repository.UnsplashNetworkRepository

class CollectionsPhotosPagingSource(
    private val repository: UnsplashNetworkRepository,
    private val id: String
) :
    PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getCollectionsPhotos(id, page)
        }.fold(
            onSuccess = {
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