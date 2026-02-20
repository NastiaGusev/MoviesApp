package com.example.data.remote.api

import com.example.data.remote.dto.ConfigurationResponse
import com.example.data.remote.dto.GenresResponse
import com.example.data.remote.dto.MoviesByGenreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genres: Int
    ): MoviesByGenreResponse

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}