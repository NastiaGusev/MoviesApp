package com.example.moviesapp.data.responses

import com.example.moviesapp.domain.model.ImageInfo

data class ConfigurationResponse(
    val change_keys: List<String>,
    val images: ImageInfo
)