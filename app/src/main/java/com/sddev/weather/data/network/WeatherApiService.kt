package com.sddev.weather.data.network

import com.sddev.weather.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather:Boolean = true,
        @Query("windspeed_unit") windSpeedUnit:String = "mph",
        @Query("hourly") hourly:String = "relativehumidity_2m,visibility,windspeed_10m"
    ): WeatherResponse
}