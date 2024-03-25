package com.abbaraees.weatherly.states

import androidx.annotation.DrawableRes
import com.abbaraees.weatherly.data.Location

data class HomeState(
    var cityName: String = "",
    var weatherDescription: String = "",
    var dateTime: String = "",
    var isLoadingLocations: Boolean = false,
    var isFetchingWeatherData: Boolean = false,
    var locations: List<Location> = emptyList(),
    @DrawableRes var weatherIcon: Int = 0,
    var temp: Double = .0,
    var searchTerm: String = "",
    var noInternet: Boolean = false,
    var error: Boolean = false

)
