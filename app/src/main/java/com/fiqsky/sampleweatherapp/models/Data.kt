package com.fiqsky.sampleweatherapp.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("weather")
    val weather: List<Weather>
)
