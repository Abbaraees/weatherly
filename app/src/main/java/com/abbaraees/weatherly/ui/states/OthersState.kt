package com.abbaraees.weatherly.ui.states

import com.abbaraees.weatherly.data.entities.WeatherData

data class OthersState(
    val weatherDataList: List<WeatherData> = emptyList()
)
