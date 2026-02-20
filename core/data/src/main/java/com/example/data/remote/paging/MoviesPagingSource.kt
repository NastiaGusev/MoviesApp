package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mapper.toDomain
import com.example.data.remote.api.MoviesApi
import com.example.domain.model.Movie

class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val genreId: Int
) : PagingSource<Int, Movie>() {

    private var totalMoviesCount = 0

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val moviesResponse = moviesApi.getMoviesByGenre(genres = genreId, page = page)
            totalMoviesCount += moviesResponse.results.size
            val movies = moviesResponse.results
                .distinctBy { it.title }
                .map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                nextKey = if (totalMoviesCount == moviesResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}