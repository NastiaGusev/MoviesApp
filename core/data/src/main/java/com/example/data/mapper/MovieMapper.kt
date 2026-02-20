package com.example.data.mapper

import com.example.data.remote.dto.ImageInfoDto
import com.example.data.remote.dto.MovieDto
import com.example.domain.model.ImageInfo
import com.example.domain.model.Movie

fun MovieDto.toDomain() = Movie(
    id = id,
    adult = adult,
    title = title,
    video = video,
    overview = overview,
    popularity = popularity,
    backdropPath = backdropPath,
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun ImageInfoDto.toDomain() = ImageInfo(
    backdropSizes = backdropSizes,
    baseUrl = baseUrl,
    logoSizes = logoSizes,
    posterSizes = posterSizes,
    profileSizes = profileSizes,
    secureBaseUrl = secureBaseUrl,
    stillSizes = stillSizes
)