package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbaraees.weatherly.data.repository.WeatherDataRepository
import com.abbaraees.weatherly.data.services.WeatherDataService
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
    private val repository: WeatherDataRepository
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
            val response = WeatherDataService.fetchWeatherData(previousData.latitude, previousData.longitude)

            if (response != null) {
                val formattedDate = Instant
                    .ofEpochSecond(response.dt)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))

                val newWeatherData = previousData.copy(
                    weatherDescription = response.weather[0].description,
                    weatherMain = response.weather[0].main,
                    temperature = response.main.temp,
                    date = formattedDate
                )
                repository.updateWeatherData(newWeatherData)
            }
        }

    }

}