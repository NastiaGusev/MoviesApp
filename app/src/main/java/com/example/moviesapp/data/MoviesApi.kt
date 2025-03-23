package com.example.moviesapp.data

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.responses.ConfigurationResponse
import com.example.moviesapp.data.responses.GenresResponse
import com.example.moviesapp.data.responses.MoviesByGenreResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApi {

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer ${BuildConfig.API_KEY}"
    )
    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer ${BuildConfig.API_KEY}"
    )
    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genres: Int
    ): MoviesByGenreResponse

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer ${BuildConfig.API_KEY}"
    )
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}