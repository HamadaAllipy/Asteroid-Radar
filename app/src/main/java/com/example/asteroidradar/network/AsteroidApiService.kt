package com.example.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class AsteroidApiFilter(val value: String) { SHOW_SAVED(value = "saved"), SHOW_TODAY(value = "today"), SHOW_WEEK(value = "week") }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl("https://api.nasa.gov/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()


interface AsteroidApiService {

    @GET(value = "planetary/apod")
    fun getImageOfDay(@Query("api_key") apiKey: String): Call<NetworkPictureOfDay>

    @GET(value = "neo/rest/v1/feed")
    fun getAsteroidsAsync(@Query("api_key") apiKey: String) : Deferred<String>


}

object AsteroidApi {

    val retrofitService = retrofit.create(AsteroidApiService::class.java)

}


