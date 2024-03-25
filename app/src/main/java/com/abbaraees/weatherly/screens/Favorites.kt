package com.abbaraees.weatherly.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.components.SearchInputField
import com.abbaraees.weatherly.components.WeatherInfoCard

@Composable
fun Favorites() {
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(10) {
                WeatherInfoCard(
                    cityName = "Washington",
                    temperature = "25 C",
                    weatherCondition = "Snow",
                    weatherIcon = R.drawable.snow,
                    isFavorite = true
                )
            }
        }
    }
}