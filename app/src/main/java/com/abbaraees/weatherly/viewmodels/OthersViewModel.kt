package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbaraees.weatherly.data.WeatherDataNetwork
import com.abbaraees.weatherly.data.repository.WeatherDataRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalCoroutinesApi::class)
class OthersViewModel(
    private val repository: WeatherDataRepository,
    private val httpClient: HttpClient,
): ViewModel() {
    private var favoritesOnly = MutableStateFlow(false)

    val weatherDataList = favoritesOnly.flatMapLatest { showFavorites ->
        when (showFavorites) {
            true -> repository.getFavorites()
            else -> repository.getAllWeatherData()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getFavoritesOnly() {
        favoritesOnly.value = true
    }

    fun markAsFavorite(weatherDataId: Int) {
        viewModelScope.launch {
            repository.markAsFavorite(weatherDataId)
        }
    }

    fun deleteWeatherData(weatherDataId: Int) {
        viewModelScope.launch {
            repository.deleteWeatherData(weatherDataId)
        }
    }

    fun updateWeatherData(weatherDataId: Int) {
        viewModelScope.launch {
            val previousData = repository.getWeatherDataById(weatherDataId)
            val weatherDataUrl =
                "https://api.openweathermap.org/data/2.5/weather?lat=${previousData.latitude}&lon=${previousData.longitude}&units=metric&appid=978074a82dda3d6d1733d990dae69123"
            var response: HttpResponse? = null
            try {
                response = httpClient.get(weatherDataUrl)
            }
            catch (_: Exception) {

            }

            if (response?.status?.value == 200) {
                val data: WeatherDataNetwork = response.body()
                val formattedDate = Instant
                    .ofEpochSecond(data.dt)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))

                val newWeatherData = previousData.copy(
                    weatherDescription = data.weather[0].description,
                    weatherMain = data.weather[0].main,
                    temperature = data.main.temp,
                    date = formattedDate
                )
                repository.updateWeatherData(newWeatherData)
            }
        }

    }

}