package com.abbaraees.weatherly.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.ui.components.SearchInputField
import com.abbaraees.weatherly.ui.components.WeatherInfoCard
import com.abbaraees.weatherly.ui.theme.WeatherlySkyBlue
import com.abbaraees.weatherly.viewmodels.OthersViewModel

@Composable
fun Recents(
    viewModel: OthersViewModel
) {
    val weatherDataList = viewModel.weatherDataList.collectAsState()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding()
            ,
            horizontalArrangement = Arrangement.End
        ) {
            SearchInputField(value = "", onValueChange = {}, {})
        }

        if (weatherDataList.value.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(weatherDataList.value) {
                    WeatherInfoCard(
                        cityName = it.locationName,
                        temperature = "${it.temperature} °C",
                        weatherCondition = it.weatherDescription,
                        weatherMain = it.weatherMain,
                        isFavorite = it.isFavorite,
                        onMarkAsFavorite = { viewModel.markAsFavorite(it.id) }
                    ) { viewModel.deleteWeatherData(it.id) }
                }
            }
        }
        else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.no_recent_locations),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = WeatherlySkyBlue
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_7_pro")
@Composable
fun PreviewRecents() {
//    Recents()
}