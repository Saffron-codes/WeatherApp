package com.sddev.weather.data.repository

import com.sddev.weather.data.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherData(lat:Double,long:Double):WeatherResponse
}