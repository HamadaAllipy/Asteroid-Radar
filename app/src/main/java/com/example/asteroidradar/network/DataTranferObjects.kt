package com.example.asteroidradar.network

import com.example.asteroidradar.database.DatabaseAsteroid
import com.example.asteroidradar.database.DatabaseImageOfDay
import com.example.asteroidradar.domain.Asteroid
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class NetworkPictureOfDay(
    val url : String,
    val date : String,
    @Json(name = "media_type") val mediaType : String,
    val title : String)


fun NetworkPictureOfDay.asDatabaseModel(): DatabaseImageOfDay {
    return DatabaseImageOfDay(
        url,
        date,
        mediaType,
        title
    )
}

fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
    return map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }.toTypedArray()
}