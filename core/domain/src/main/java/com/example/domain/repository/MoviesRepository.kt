package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.Genre
import com.example.domain.model.ImageInfo
import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getGenres(): List<Genre>
    suspend fun getConfiguration(): ImageInfo
    fun getMovies(genreId: Int): Flow<PagingData<Movie>>
}