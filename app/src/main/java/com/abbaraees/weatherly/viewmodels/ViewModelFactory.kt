package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abbaraees.weatherly.data.repository.WeatherDataRepository
import io.ktor.client.HttpClient

class ViewModelFactory(
    private val httpClient: HttpClient,
    private val repository: WeatherDataRepository,
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(httpClient, repository) as T
            }
            else -> OthersViewModel(repository, httpClient) as T
        }
    }
}