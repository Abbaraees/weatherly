package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbaraees.weatherly.data.repository.WeatherDataRepository
import com.abbaraees.weatherly.ui.states.OthersState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class OthersViewModel(
    private val repository: WeatherDataRepository,
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

}