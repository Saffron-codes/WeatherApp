package com.sddev.weather.utils

import com.sddev.weather.ui.adapters.HourlyData

fun createHourlyDataList(list1: List<String>, list2: List<Any>): List<HourlyData> {
    val hourlyDataList = mutableListOf<HourlyData>()

    val minSize = minOf(list1.size, list2.size)
    for (i in 0 until minSize) {
        val time = list1[i]
        val windSpeed = list2[i]
        hourlyDataList.add(HourlyData(time, windSpeed))
    }

    return hourlyDataList
}
