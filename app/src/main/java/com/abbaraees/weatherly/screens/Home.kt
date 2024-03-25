package com.abbaraees.weatherly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.components.FetchingWeatherData
import com.abbaraees.weatherly.components.SearchInputField
import com.abbaraees.weatherly.components.WeatherInfo
import com.abbaraees.weatherly.ui.theme.WeatherlySkyBlue
import com.abbaraees.weatherly.viewmodels.HomeViewModel
import kotlinx.coroutines.launch


@Composable
fun Home(
    viewModel: HomeViewModel
) {
    val state = viewModel.state.collectAsState()
    val searchScope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchInputField(
                value = state.value.searchTerm,
                onValueChange = { viewModel.setSearchTerm(it) },
                onSearch = {
                    searchScope.launch {
                        viewModel.getLocations()
                    }
                },
                modifier = Modifier.padding(4.dp)
            )
            if (state.value.locations.isNotEmpty() || state.value.isLoadingLocations) {
                Column(
                    Modifier
                        .fillMaxWidth(0.81f)
                        .shadow(1.dp, shape = RoundedCornerShape(5.dp), clip = true)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.value.isLoadingLocations) {
                        CircularProgressIndicator(
                            Modifier.padding(8.dp),
                            color = WeatherlySkyBlue,
                            strokeWidth = 4.dp
                        )
                    }
                    else {
                        state.value.locations.forEach { location ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        searchScope.launch {
                                            viewModel.fetchWeatherData(
                                                location.latitude,
                                                location.longitude
                                            )
                                        }
                                    }
                            ) {
                                Text(
                                    text = location.name,
                                    style = MaterialTheme.typography.labelLarge,
                                )
                                Text(
                                    text = "${location.admin1}, ${location.country}",
                                    color = Color.Gray
                                )
                            }
                            Divider(thickness = 1.dp, color = Color.LightGray)
                        }
                    }
                }
            }
        }

        if (state.value.noInternet) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_internet),
                    contentDescription = "No internet connection",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "No Internet Connection",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = WeatherlySkyBlue
                )
            }
        }
        else {
            if (state.value.cityName.isBlank() && !state.value.isFetchingWeatherData) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Please Enter a city name",
                        style = MaterialTheme.typography.headlineMedium,
                        color = WeatherlySkyBlue
                    )
                }
            } else {
                if (state.value.isFetchingWeatherData) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        FetchingWeatherData()
                    }
                } else {
                    WeatherInfo(
                        cityName = state.value.cityName,
                        temperature = "${state.value.temp} °C",
                        weatherCondition = state.value.weatherDescription,
                        time = state.value.dateTime,
                        weatherIcon = state.value.weatherIcon,
                        modifier = Modifier
                            .fillMaxWidth(.75f)
                            .padding(8.dp)
                    )
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHome() {
//    Home()
}