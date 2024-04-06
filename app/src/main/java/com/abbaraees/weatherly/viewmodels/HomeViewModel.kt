package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.data.LocationResponse
import com.abbaraees.weatherly.data.WeatherDataNetwork
import com.abbaraees.weatherly.data.entities.WeatherData
import com.abbaraees.weatherly.data.repository.WeatherDataRepository
import com.abbaraees.weatherly.ui.states.HomeState
import io.ktor.client.HttpClient
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
    private val httpClient: HttpClient,
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
        val weatherDataUrl = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&units=metric&appid=978074a82dda3d6d1733d990dae69123"
        viewModelScope.launch {
            var response: HttpResponse? = null
            try {
                response = httpClient.get(weatherDataUrl)
            }
            catch (error: Exception) {
                _state.update {
                    it.copy(
                        noInternet = true,
                        isFetchingWeatherData = false
                    )
                }
            }
            if (response != null) {
                if (response.status.value == 200) {
                    val data: WeatherDataNetwork = response.body()
                    val formattedDate = Instant
                        .ofEpochSecond(data.dt)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
                    _state.update {
                        it.copy(
                            cityName = data.name,
                            weatherDescription = data.weather[0].description,
                            dateTime = formattedDate,
                            temp = data.main.temp,
                            isFetchingWeatherData = false,
                            weatherIcon = when (data.weather[0].main) {
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
                        weatherMain = data.weather[0].main,
                        weatherDescription = data.weather[0].description,
                        temperature = data.main.temp,
                        date = formattedDate
                    )

                    viewModelScope.launch {
                        repository.addWeatherData(weatherData)
                    }

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
        val locationUrl = "https://geocoding-api.open-meteo.com/v1/search?name=${_state.value.searchTerm}"
        viewModelScope.launch {
            var response: HttpResponse? = null
            try {
                response = httpClient.get(locationUrl)
            }
            catch (error: Exception) {
                _state.update {
                    it.copy(
                        noInternet = true,
                        isLoadingLocations = false
                    ) }
            }
            if (response != null) {
                if (response.status.value == 200) {
                    val data: LocationResponse = response.body()
                    _state.update {
                        it.copy(
                            locations = data.results,
                            isLoadingLocations = false
                        )
                    }
                }
            }
        }
    }

    fun setSearchTerm(value: String) {
        _state.update {
            it.copy(searchTerm = value)
        }
    }
}