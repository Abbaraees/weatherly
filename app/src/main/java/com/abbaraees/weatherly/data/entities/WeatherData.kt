package com.abbaraees.weatherly.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WeatherData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val locationName: String,
    val state: String,
    val country: String,
    var weatherDescription: String,
    var temperature: Double,
    var date: String,
    var isCurrentLocation: Boolean = false,
    var isFavorite: Boolean = false,
    val weatherMain: String
)
