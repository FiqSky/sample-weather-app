package com.fiqsky.sampleweatherapp.api

import com.fiqsky.sampleweatherapp.models.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {
    @GET("/data/2.5/weather?")
    suspend fun getWeather(@Query("q") q: String, @Query("appid") appid: String): Data
}