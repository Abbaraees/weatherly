package com.abbaraees.weatherly.data.services

import com.abbaraees.weatherly.data.WeatherDataNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson


object WeatherDataService {

    // Initialize an HttpClient with Gson content negotiation for JSON handling
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    /**
     * Fetches weather data from the OpenWeatherMap API based on latitude and longitude coordinates.
     *
     * @param latitude The latitude coordinate of the location.
     * @param longitude The longitude coordinate of the location.
     * @return WeatherDataNetwork object containing weather information or null if the request fails.
     */
    suspend fun fetchWeatherData(latitude: Double, longitude: Double): WeatherDataNetwork? {
        var weatherData: WeatherDataNetwork? = null
        var response: HttpResponse? = null

        // Construct the API URL with latitude and longitude coordinates, units, and API key
        val weatherDataUrl = "https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&units=metric&appid=978074a82dda3d6d1733d990dae69123"

        try {
            // Send GET request to the API
            response = httpClient.get(weatherDataUrl)
        } catch(_: Exception) {
            // Catch any exceptions that occur during the request
            // Do nothing as we handle the null response below
        }

        // Check if the response status is 200 (OK)
        if (response?.status?.value == 200) {
            // Parse the response body into WeatherDataNetwork object
            weatherData = response.body()
        }

        // Return the fetched weather data or null if the request fails
        return weatherData
    }
}
