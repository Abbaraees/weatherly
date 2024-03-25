package com.abbaraees.weatherly.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.components.SearchInputField
import com.abbaraees.weatherly.components.WeatherInfoCard

@Composable
fun Recents() {
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
                    temperature = "32 C",
                    weatherCondition = "Sunny",
                    weatherIcon = R.drawable.sun,
                    isFavorite = it % 2 == 0
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_7_pro")
@Composable
fun PreviewRecents() {
    Recents()
}