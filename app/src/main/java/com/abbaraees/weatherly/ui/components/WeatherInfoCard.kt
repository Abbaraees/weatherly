package com.abbaraees.weatherly.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.ui.theme.WeatherlySkyBlue
import com.abbaraees.weatherly.ui.theme.WeatherlySunshineYellow

@Composable
fun WeatherInfoCard(
    cityName: String,
    temperature: String,
    weatherCondition: String,
    weatherMain: String,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onMarkAsFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        border = BorderStroke(1.dp, WeatherlySkyBlue),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row {
            Image(
                painter = painterResource(
                    id = when (weatherMain) {
                        "Clouds" -> R.drawable.cloudy
                        "Rain" -> R.drawable.rainy
                        "Snow" -> R.drawable.snow
                        "Thunderstorm" -> R.drawable.thunderstorm
                        else -> R.drawable.sun
                    }
                ),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.40f)
                    .padding(8.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                    ,
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete weather data",
                            tint = WeatherlySunshineYellow
                        )
                    }
                    IconButton(onClick = onMarkAsFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Mark as favorite",
                            tint = if (isFavorite) WeatherlySkyBlue else Color.LightGray
                        )
                    }
                }
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = temperature,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = weatherCondition,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWeatherInfoCard() {
//    WeatherInfoCard(
//        cityName = "Washington",
//        temperature = "32 C",
//        weatherCondition = "Sunny",
//        weatherIcon = R.drawable.sun,
//        isFavorite = true,
//        onMarkAsFavorite = { viewModel.markAsFavorite(it.id) }
//    ) { viewModel.deleteWeatherData(it.id) }
}