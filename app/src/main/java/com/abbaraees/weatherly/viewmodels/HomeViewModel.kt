package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.data.LocationResponse
import com.abbaraees.weatherly.data.entities.WeatherData
import com.abbaraees.weatherly.data.repository.WeatherDataRepository
import com.abbaraees.weatherly.data.services.LocationService
import com.abbaraees.weatherly.data.services.WeatherDataService
import com.abbaraees.weatherly.ui.states.HomeState
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class HomeViewModel(
    private val repository: WeatherDataRepository,
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    val state = _state

    /**
     * Fetches weather data based on the provided latitude and longitude.
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @param name Name of the location.
     * @param state State of the location.
     * @param country Country of the location.
     */
    fun fetchWeatherData(lat: Double, lon: Double, name: String, state: String, country: String) {
        _state.update { it.copy(
            isFetchingWeatherData = true,
            isLoadingLocations = false,
            locations = emptyList(),
            searchTerm = "",
            noInternet = false
        ) }

        viewModelScope.launch {
            val response = WeatherDataService.fetchWeatherData(lat, lon)
            if (response != null) {
                val formattedDate = Instant
                    .ofEpochSecond(response.dt)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
                _state.update {
                    it.copy(
                        cityName = response.name,
                        weatherDescription = response.weather[0].description,
                        dateTime = formattedDate,
                        temp = response.main.temp,
                        isFetchingWeatherData = false,
                        weatherIcon = when (response.weather[0].main) {
                            "Clouds" -> R.drawable.cloudy
                            "Rain" -> R.drawable.rainy
                            "Snow" -> R.drawable.snow
                            "Thunderstorm" -> R.drawable.thunderstorm
                            else -> R.drawable.sun
                        }
                    )
                }

                val weatherData = WeatherData(
                    latitude = lat,
                    longitude = lon,
                    locationName = name,
                    state = state,
                    country = country,
                    weatherMain = response.weather[0].main,
                    weatherDescription = response.weather[0].description,
                    temperature = response.main.temp,
                    date = formattedDate
                )

                val previousData = repository.getWeatherDataByLatitudeAndLongitude(lat, lon)
                if (previousData != null) {
                    val newWeatherData = previousData.copy(
                        weatherDescription = weatherData.weatherDescription,
                        weatherMain = weatherData.weatherMain,
                        temperature = weatherData.temperature,
                        date = formattedDate
                    )
                    repository.updateWeatherData(newWeatherData)
                } else {
                    repository.addWeatherData(weatherData)
                }
            }
            else {
                _state.update {
                    it.copy(
                        noInternet = true,
                        isFetchingWeatherData = false
                    )
                }
            }
        }
    }

    fun getLocations() {
        _state.update {
            it.copy(
                isLoadingLocations = true,
                noInternet = false
            )
        }
        viewModelScope.launch {
            val response: LocationResponse? = LocationService.getLocations(_state.value.searchTerm)

            if (response != null) {
                _state.update {
                    it.copy(
                        locations = response.results,
                        isLoadingLocations = false
                    )
                }
            }
            else {
                _state.update {
                    it.copy(
                        noInternet = true,
                        isLoadingLocations = false
                    ) }
            }
        }
    }

    fun setSearchTerm(value: String) {
        _state.update {
            it.copy(searchTerm = value)
        }
    }
}