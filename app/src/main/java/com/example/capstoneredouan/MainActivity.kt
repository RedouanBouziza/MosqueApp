package com.example.capstoneredouan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneredouan.data.api.PrayerTimesApi
import com.example.capstoneredouan.ui.view.Login
import com.example.capstoneredouan.ui.view.Registration
import com.example.capstoneredouan.ui.viewmodel.LoginViewModel
import com.example.capstoneredouan.ui.viewmodel.PrayerTimesViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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

//TODO: make login work

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun NavHostScreen(
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val loginViewModel: LoginViewModel = viewModel()
    val prayerTimesViewModel: PrayerTimesViewModel = viewModel()
//    val userId = Firebase.auth.currentUser?.uid ?: ""
    val currentUser = Firebase.auth.currentUser


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.PrayerTimesScreen.route,
        ) {
            composable(Screen.HomeScreen.route) {
                Home()
            }
            composable(Screen.PrayerTimesScreen.route) {
                PrayerTimes(navController, prayerTimesViewModel, loginViewModel)
            }
            composable(Screen.MapScreen.route) {
                Map()
            }
            composable(Screen.CompassScreen.route) {
                Compass()
            }
            composable(Screen.Login.route) {
                Login(navController, loginViewModel)
            }
            composable(Screen.Registration.route) {
                Registration(navController, loginViewModel)
            }
        }

        if (currentUser == null) {
            navController.navigate(Screen.Login.route)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    //create bottom bar for the app

}

@Composable
fun TopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(text = "Redouan Mosque App")
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Icon(Icons.Filled.Person, contentDescription = "Login")
            }
        }
    )
}
