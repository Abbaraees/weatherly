package com.abbaraees.weatherly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.abbaraees.weatherly.data.WeatherlyDatabase
import com.abbaraees.weatherly.data.repository.WeatherDataRepository
import com.abbaraees.weatherly.ui.components.WeatherlyBottomBar
import com.abbaraees.weatherly.ui.navigation.WeatherlyNavHost
import com.abbaraees.weatherly.ui.components.WeatherlyTopBar
import com.abbaraees.weatherly.ui.theme.WeatherlyTheme
import com.abbaraees.weatherly.viewmodels.ViewModelFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson


class MainActivity : ComponentActivity() {
    private lateinit var db: WeatherlyDatabase
    private lateinit var repository: WeatherDataRepository
    private lateinit var factory: ViewModelFactory

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        db = Room.databaseBuilder(
                applicationContext,
                WeatherlyDatabase::class.java,
                "weatherlyDatabase.db"
            ).build()
        repository = WeatherDataRepository(db.weatherDataDao())
        factory = ViewModelFactory(httpClient, repository)

        super.onCreate(savedInstanceState)
        setContent {
            WeatherlyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Weatherly(
                        factory = factory
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Weatherly(factory: ViewModelFactory) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WeatherlyTopBar()
        },
        bottomBar = {
            WeatherlyBottomBar(navController = navController)
        }
    ) {
        WeatherlyNavHost(
            navController = navController,
            factory = factory,
            modifier = Modifier.padding(it))
    }
}