package com.example.moviesapp.data.responses

import com.example.moviesapp.domain.model.Movie

data class MoviesByGenreResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)