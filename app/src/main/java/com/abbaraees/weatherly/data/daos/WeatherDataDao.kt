package com.abbaraees.weatherly.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.abbaraees.weatherly.data.entities.WeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDataDao {
    @Upsert
    suspend fun addWeatherData(weatherData: WeatherData)

    @Delete
    suspend fun deleteWeatherData(weatherData: WeatherData)

    @Query("SELECT * FROM weatherdata WHERE id = :id")
    suspend fun getWeatherById(id: Int): WeatherData

    @Query("SELECT * FROM weatherdata")
    fun getAllWeatherData(): Flow<List<WeatherData>>

    @Query("SELECT * FROM weatherdata WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<WeatherData>>

    @Query("SELECT * FROM weatherdata WHERE latitude = :latitude AND longitude = :longitude")
    suspend fun getWeatherDataByLatitudeAndLongitude(latitude: Double, longitude: Double): WeatherData?
}