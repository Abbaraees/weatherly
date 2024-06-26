package com.abbaraees.weatherly.data.repository

import com.abbaraees.weatherly.data.daos.WeatherDataDao
import com.abbaraees.weatherly.data.entities.WeatherData
import kotlinx.coroutines.flow.Flow

class WeatherDataRepository(
    private val weatherDataDao: WeatherDataDao
) {

    suspend fun addWeatherData(weatherData: WeatherData) {
        weatherDataDao.addWeatherData(weatherData)
    }

    suspend fun deleteWeatherData(weatherData: WeatherData) {
        weatherDataDao.deleteWeatherData(weatherData)
    }

    suspend fun getWeatherDataById(id: Int): WeatherData {
        return weatherDataDao.getWeatherById(id)
    }

    fun getAllWeatherData(): Flow<List<WeatherData>> {
        return weatherDataDao.getAllWeatherData()
    }

    fun getFavorites(): Flow<List<WeatherData>> {
        return weatherDataDao.getFavorites()
    }

    suspend fun markAsFavorite(weatherDataId: Int) {
        val weatherData = weatherDataDao.getWeatherById(weatherDataId)
        weatherData.isFavorite = !weatherData.isFavorite
        weatherDataDao.addWeatherData(weatherData)
    }

    suspend fun deleteWeatherData(weatherDataId: Int) {
        val weatherData = weatherDataDao.getWeatherById(weatherDataId)
        weatherDataDao.deleteWeatherData(weatherData)
    }

    suspend fun getWeatherDataByLatitudeAndLongitude(latitude: Double, longitude: Double): WeatherData? {
        return weatherDataDao.getWeatherDataByLatitudeAndLongitude(latitude, longitude)
    }

    suspend fun updateWeatherData(weatherData: WeatherData) {
        weatherDataDao.addWeatherData(weatherData)
    }
}