package com.arnava.photohub.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import javax.inject.Inject

class SearchPhotoPagingSource (
    private val repository: UnsplashNetworkRepository,
    private val query: String
) :
    PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int  = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getFoundPhotoList(page,query)
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