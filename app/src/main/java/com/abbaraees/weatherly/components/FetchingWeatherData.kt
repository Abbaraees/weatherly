package com.abbaraees.weatherly.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abbaraees.weatherly.ui.theme.WeatherlySkyBlue

@Composable
fun FetchingWeatherData(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
       CircularProgressIndicator(
           Modifier.size(50.dp),
           color = WeatherlySkyBlue,
           strokeWidth = 4.dp
       )
        Text(
            text = "Fetching Weather Data",
            style = MaterialTheme.typography.headlineMedium,
            color = WeatherlySkyBlue
        )
    }
}