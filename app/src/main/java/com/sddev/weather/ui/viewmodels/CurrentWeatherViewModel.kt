package com.sddev.weather.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sddev.weather.data.network.RetrofitClient
import com.sddev.weather.data.model.WeatherResponse
import com.sddev.weather.data.repository.WeatherRepository
import com.sddev.weather.data.repository.WeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class WeatherState {
    data object Loading : WeatherState()
    data class Success(val data: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
):ViewModel() {

    private val _weatherState = MutableLiveData<WeatherState>(WeatherState.Loading)
    val weatherState: MutableLiveData<WeatherState> = _weatherState

    fun loadWeatherData(){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _weatherState.postValue(WeatherState.Loading)
                val data = weatherRepository.getWeatherData(13.0,83.1)
                _weatherState.postValue(WeatherState.Success(data))
            } catch (e:Exception){
                _weatherState.postValue(WeatherState.Error("Error fetching weather data"))
            }
        }
    }
}