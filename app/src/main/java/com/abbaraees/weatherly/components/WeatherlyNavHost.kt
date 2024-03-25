package com.abbaraees.weatherly.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abbaraees.weatherly.screens.Favorites
import com.abbaraees.weatherly.screens.Home
import com.abbaraees.weatherly.screens.Recents
import com.abbaraees.weatherly.screens.WeatherlyScreen
import com.abbaraees.weatherly.viewmodels.ViewModelFactory
import io.ktor.client.HttpClient

@Composable
fun WeatherlyNavHost(
    navController: NavHostController,
    modifier: Modifier,
    httpClient: HttpClient,
    factory: ViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = WeatherlyScreen.route_home,
        modifier = modifier
    ) {
        composable(WeatherlyScreen.route_home) {
            Home(viewModel = viewModel(factory = factory))
        }
        composable(WeatherlyScreen.route_recents) {
            Recents()
        }
        composable(WeatherlyScreen.route_favorites) {
            Favorites()
        }
    }
}