package com.example.moviesapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.domain.model.Movie

class MoviesPagingSource(private val moviesApi: MoviesApi) : PagingSource<Int, Movie>() {

    private var totalMoviesCount = 0

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val genre = 10751
        return try {
            val moviesResponse = moviesApi.getMoviesByGenre(genres = genre, page = page)
            totalMoviesCount += moviesResponse.results.size
            val articles = moviesResponse.results.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                nextKey = if (totalMoviesCount == moviesResponse.total_results) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}