package com.sddev.weather.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val temperature: Double,
    @SerializedName("windspeed")
    val windSpeed: Double,
    @SerializedName("winddirection")
    val windDirection: Double,
    @SerializedName("weathercode")
    val weatherCode: Int,
    @SerializedName("is_day")
    val isDay: Int,
    val time: String
)

