package com.abbaraees.weatherly.data



data class WeatherDataNetwork(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)


data class Coord(
    val lon: Double,
    val lat: Double
)


data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)


data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)


data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class Clouds(
    val all: Int
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class LocationResponse(
    val results: List<Location>,
    val generationtime_ms: Double
)

data class Location(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Double,
    val feature_code: String,
    val country_code: String,
    val admin1_id: Int,
    val admin2_id: Int,
    val admin3_id: Int,
    val timezone: String,
    val population: Int,
    val country_id: Int,
    val country: String,
    val admin1: String,
    val admin2: String,
    val admin3: String
)
