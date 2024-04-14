package com.abbaraees.weatherly.data.services

import com.abbaraees.weatherly.data.LocationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson

object LocationService {

    // Initialize an HttpClient with Gson content negotiation for JSON handling
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    /**
     * Fetches location data from the Open-Meteo geocoding API based on location name.
     *
     * @param name The name of the location to search for.
     * @return LocationResponse object containing location information or null if the request fails.
     */
    suspend fun getLocations(name: String): LocationResponse? {
        var locationResponse: LocationResponse? = null
        var httpResponse: HttpResponse? = null

        // Construct the API URL with the location name
        val locationUrl = "https://geocoding-api.open-meteo.com/v1/search?name=$name"

        try {
            // Send GET request to the API
            httpResponse = httpClient.get(locationUrl)
        } catch (_: Exception) {
            // Catch any exceptions that occur during the request
            // Do nothing as we handle the null response below
        }

        // Check if the response status is 200 (OK)
        if (httpResponse?.status?.value == 200) {
            // Parse the response body into LocationResponse object
            locationResponse = httpResponse.body()
        }

        // Return the fetched location data or null if the request fails
        return locationResponse
    }
}
