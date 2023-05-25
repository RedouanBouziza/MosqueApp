package com.example.capstoneredouan

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.capstoneredouan.ui.theme.CapstoneRedouanTheme
import com.example.capstoneredouan.ui.view.Home
import com.example.capstoneredouan.ui.view.Map
import com.example.capstoneredouan.ui.view.Compass
import com.example.capstoneredouan.ui.view.PrayerTimes
import com.example.capstoneredouan.ui.view.route.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import com.example.capstoneredouan.data.api.PrayerTimesApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenLayout()
        PrayerTimesApi.initialize(this)
    }

    private fun screenLayout() {
        setContent {
            CapstoneRedouanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHostScreen(navController)
                }
            }
        }
    }
}


@Composable
private fun NavHostScreen(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.PrayerTimesScreen.route,
    ) {
        composable(Screen.HomeScreen.route) {
            Home()
        }
        composable(Screen.PrayerTimesScreen.route) {
            PrayerTimes()
        }
        composable(Screen.MapScreen.route) {
            Map()
        }
        composable(Screen.CompassScreen.route) {
            Compass()
        }
    }
}