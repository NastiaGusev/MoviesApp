package com.example.moviesapp.presentation.movies

import androidx.paging.PagingData
import com.example.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class MoviesState(
    val genreId: Int = 0,
    val movies: Flow<PagingData<Movie>>? = null
)