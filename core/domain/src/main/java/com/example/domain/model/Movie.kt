package com.example.domain.model

data class Movie(
    val id: Int,
    val adult: Boolean,
    val title: String,
    val video: Boolean,
    val overview: String,
    val popularity: Double,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
)