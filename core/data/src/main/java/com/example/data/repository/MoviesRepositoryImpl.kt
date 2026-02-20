package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.mapper.toDomain
import com.example.data.remote.api.MoviesApi
import com.example.data.remote.paging.MoviesPagingSource
import com.example.domain.model.Genre
import com.example.domain.model.ImageInfo
import com.example.domain.model.Movie
import com.example.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi
) : MoviesRepository {

    override suspend fun getGenres(): List<Genre> {
        return moviesApi.getGenres().genres
    }

    override suspend fun getConfiguration(): ImageInfo {
        return moviesApi.getConfiguration().images.toDomain()
    }

    override fun getMovies(genreId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                MoviesPagingSource(
                    moviesApi = moviesApi,
                    genreId = genreId,
                )
            }
        ).flow
    }

}