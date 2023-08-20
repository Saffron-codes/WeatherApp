package com.sddev.weather.data.repository

import com.sddev.weather.data.model.WeatherResponse
import com.sddev.weather.data.network.WeatherApiService

class WeatherRepositoryImpl(private val apiService: WeatherApiService):WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): WeatherResponse {
        return apiService.getWeather(latitude = lat, longitude = long,true)
    }

}