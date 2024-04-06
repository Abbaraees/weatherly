package com.abbaraees.weatherly.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.abbaraees.weatherly.R

sealed class WeatherlyScreen(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    
    companion object {

        val screens = listOf(
            Home,
            Recents,
            Favorites
        )
        
        const val route_home = "home"
        const val route_recents = "recents"
        const val route_favorites = "favorites"
    }
    
    
    object Home: WeatherlyScreen(
        route_home,
        R.string.home,
        Icons.Default.Home
    )

    object Recents: WeatherlyScreen(
        route_recents,
        R.string.recents,
        Icons.Default.Menu
    )

    object Favorites: WeatherlyScreen(
        route_favorites,
        R.string.favorites,
        Icons.Default.Favorite
    )
}