package com.arnava.photohub.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import javax.inject.Inject

class CollectionPagingSource (private val repository: UnsplashNetworkRepository) :
    PagingSource<Int, PhotoCollection>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoCollection>): Int  = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoCollection> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getCollectionList(page)
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