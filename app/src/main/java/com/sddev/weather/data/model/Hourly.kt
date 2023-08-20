package com.sddev.weather.data.model

import com.google.gson.annotations.SerializedName

data class Hourly(
    val time:List<String>,
    @SerializedName("relativehumidity_2m")
    val humidity:List<Int>,
    val visibility:List<Int>,
    @SerializedName("windspeed_10m")
    val windSpeed:List<Double>
)
