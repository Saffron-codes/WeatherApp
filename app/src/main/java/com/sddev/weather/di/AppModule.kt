package com.sddev.weather.di

import android.content.Context
import com.sddev.weather.BaseApplication
import com.sddev.weather.data.network.RetrofitClient
import com.sddev.weather.data.network.WeatherApiService
import com.sddev.weather.data.repository.WeatherRepository
import com.sddev.weather.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app:Context):BaseApplication{
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(apiService: WeatherApiService):WeatherRepository{
        return WeatherRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideApiService():WeatherApiService{
        return RetrofitClient.weatherApi
    }
}