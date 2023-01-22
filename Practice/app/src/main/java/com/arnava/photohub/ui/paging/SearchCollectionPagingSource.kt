package com.arnava.photohub.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.photohub.data.models.unsplash.collection.FoundCollection
import com.arnava.photohub.data.models.unsplash.collection.PhotoCollection
import com.arnava.photohub.data.repository.UnsplashNetworkRepository
import com.arnava.photohub.data.models.unsplash.photo.UnsplashPhoto
import javax.inject.Inject

class SearchCollectionPagingSource (
    private val repository: UnsplashNetworkRepository,
    private val query: String
    ) :
    PagingSource<Int, FoundCollection>() {
    override fun getRefreshKey(state: PagingState<Int, FoundCollection>): Int  = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoundCollection> {
        val page = params.key ?: FIRST_PAGE
//        val res = repository.getFoundCollectionList(page,query)
        return kotlin.runCatching {
            repository.getFoundCollectionList(page,query)
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