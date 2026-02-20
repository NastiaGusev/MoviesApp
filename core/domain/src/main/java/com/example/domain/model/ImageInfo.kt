package com.example.domain.model

data class ImageInfo(
    val backdropSizes: List<String>,
    val baseUrl: String,
    val logoSizes: List<String>,
    val posterSizes: List<String>,
    val profileSizes: List<String>,
    val secureBaseUrl: String,
    val stillSizes: List<String>
)