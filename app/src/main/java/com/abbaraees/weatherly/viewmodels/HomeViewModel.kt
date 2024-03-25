package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbaraees.weatherly.data.LocationResponse
import com.abbaraees.weatherly.data.WeatherDataNetwork
import com.abbaraees.weatherly.states.HomeState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.util.Date


class HomeViewModel(
    private val httpClient: HttpClient
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    val state = _state

    fun fetchWeatherData(lat: Double, lon: Double) {
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
                    _state.update {
                        it.copy(
                            cityName = data.name,
                            weatherDescription = data.weather[0].main,
                            dateTime = Instant
                                .ofEpochSecond(data.dt)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime().toString(),
                            temp = data.main.temp,
                            isFetchingWeatherData = false
                        )
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