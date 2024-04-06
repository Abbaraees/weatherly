package com.abbaraees.weatherly.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abbaraees.weatherly.data.daos.WeatherDataDao
import com.abbaraees.weatherly.data.entities.WeatherData

@Database(
    entities = [WeatherData::class],
    version = 1
)
abstract class WeatherlyDatabase: RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}