package com.abbaraees.weatherly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abbaraees.weatherly.ui.screens.Favorites
import com.abbaraees.weatherly.ui.screens.Home
import com.abbaraees.weatherly.ui.screens.Recents
import com.abbaraees.weatherly.ui.screens.WeatherlyScreen
import com.abbaraees.weatherly.viewmodels.ViewModelFactory

@Composable
fun WeatherlyNavHost(
    navController: NavHostController,
    modifier: Modifier,
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
            Recents(viewModel = viewModel(factory = factory))
        }
        composable(WeatherlyScreen.route_favorites) {
            Favorites(viewModel = viewModel(factory = factory))
        }

    }
}