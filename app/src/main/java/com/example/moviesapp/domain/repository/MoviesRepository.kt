package com.example.moviesapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesapp.data.MoviesApi
import com.example.moviesapp.data.MoviesPagingSource
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.model.ImageInfo
import com.example.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val moviesApi: MoviesApi
) {

    suspend fun getGenres(): List<Genre> {
        return moviesApi.getGenres().genres
    }

    suspend fun getConfiguration(): ImageInfo {
        return moviesApi.getConfiguration().images
    }

    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                MoviesPagingSource(
                    moviesApi = moviesApi
                )
            }
        ).flow
    }

}