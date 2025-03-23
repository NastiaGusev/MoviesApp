package com.example.moviesapp.presentation

import androidx.paging.PagingData
import com.example.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class MoviesState(
    val genreId: Int = 0,
    val movies: Flow<PagingData<Movie>>? = null
)