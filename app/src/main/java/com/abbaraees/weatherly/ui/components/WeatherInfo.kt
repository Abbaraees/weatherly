package com.abbaraees.weatherly.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abbaraees.weatherly.R

@Composable
fun WeatherInfo(
    cityName: String,
    temperature: String,
    weatherCondition: String,
    time: String,
    @DrawableRes weatherIcon: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = "Weather Icon",
            contentScale = ContentScale.Fit
        )
        Text(
            text = temperature,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = weatherCondition,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = time,
            style = MaterialTheme.typography.labelLarge
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWeatherInfo() {
    WeatherInfo(
        cityName = "Daura",
        temperature = "32 C",
        weatherCondition = "Partly Cloudy",
        time = "7:30 AM",
        weatherIcon = R.drawable.cloudy
    )
}
