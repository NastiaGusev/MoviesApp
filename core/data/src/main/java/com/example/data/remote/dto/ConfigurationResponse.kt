package com.example.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("change_keys") val changeKeys: List<String>,
    val images: ImageInfoDto
)