package com.example.asteroidradar.domain

import com.squareup.moshi.Json

data class ImageOfDay(
    val url: String,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
)