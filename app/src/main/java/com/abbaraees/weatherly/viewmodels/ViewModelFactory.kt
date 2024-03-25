package com.abbaraees.weatherly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient

class ViewModelFactory(
    private val httpClient: HttpClient
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(httpClient) as T
        else
            return super.create(modelClass)
    }
}