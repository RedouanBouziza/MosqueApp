package com.example.capstoneredouan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstoneredouan.ui.theme.CapstoneRedouanTheme
import com.example.capstoneredouan.ui.view.Home
import com.example.capstoneredouan.ui.view.Map
import com.example.capstoneredouan.ui.view.Compass
import com.example.capstoneredouan.ui.view.PrayerTimes
import com.example.capstoneredouan.ui.view.route.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenLayout()
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
        startDestination = Screen.HomeScreen.route,
    ) {
        composable(Screen.HomeScreen.route){
            Home()
        }
        composable(Screen.PrayerTimesScreen.route){
            PrayerTimes()
        }
        composable(Screen.MapScreen.route){
            Map()
        }
        composable(Screen.CompassScreen.route){
            Compass()
        }
    }
}