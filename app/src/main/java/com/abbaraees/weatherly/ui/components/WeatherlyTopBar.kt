package com.abbaraees.weatherly.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.abbaraees.weatherly.R
import com.abbaraees.weatherly.ui.theme.WeatherlySkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherlyTopBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = WeatherlySkyBlue
        ),
        modifier = modifier
    )
}