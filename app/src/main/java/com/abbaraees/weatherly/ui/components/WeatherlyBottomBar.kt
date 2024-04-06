package com.abbaraees.weatherly.ui.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abbaraees.weatherly.ui.screens.WeatherlyScreen
import com.abbaraees.weatherly.ui.theme.WeatherlySkyBlue

@Composable
fun WeatherlyBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = WeatherlySkyBlue
    ) {
        val navStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navStackEntry?.destination
        WeatherlyScreen.screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                label = {
                    Text(text = stringResource(id = screen.label))
                },
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.label)
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}