package com.example.capstoneredouan

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstoneredouan.ui.theme.CapstoneRedouanTheme
import com.example.capstoneredouan.ui.view.map.Map
import com.example.capstoneredouan.ui.view.compass.Compass
import com.example.capstoneredouan.ui.view.prayertimes.PrayerTimes
import com.example.capstoneredouan.ui.view.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.capstoneredouan.data.api.PrayerTimesApi
import com.example.capstoneredouan.data.model.MapState
import com.example.capstoneredouan.data.utils.Resource
import com.example.capstoneredouan.ui.view.auth.Login
import com.example.capstoneredouan.ui.view.auth.Registration
import com.example.capstoneredouan.ui.viewmodel.LoginViewModel
import com.example.capstoneredouan.ui.viewmodel.MapViewModel
import com.example.capstoneredouan.ui.viewmodel.PrayerTimesViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getDeviceLocation(fusedLocationProviderClient)
            }
        }

    private fun askPermissions() = when {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {
            viewModel.getDeviceLocation(fusedLocationProviderClient)
        }
        else -> {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()
        screenLayout()
        PrayerTimesApi.initialize(this)
        FirebaseApp.initializeApp(this);
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
                    NavHostScreen(navController, viewModel.state.value)
                }
            }
        }
    }
}


//TODO: make login work

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun NavHostScreen(
    navController: NavHostController,
    viewModel: MapState
) {
    val scaffoldState = rememberScaffoldState()
    val loginViewModel: LoginViewModel = viewModel()
    val prayerTimesViewModel: PrayerTimesViewModel = viewModel()

    val currentUser = Firebase.auth.currentUser


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(navController = navController, loginViewModel = loginViewModel)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.PrayerTimesScreen.route,
        ) {
            composable(Screen.PrayerTimesScreen.route) {
                PrayerTimes(prayerTimesViewModel)
            }
            composable(Screen.MapScreen.route) {
                Map(state = viewModel)
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
    val currentUser = Firebase.auth.currentUser
    val currentRoute = currentRoute(navController)

    val items = listOf(
        Screen.PrayerTimesScreen,
        Screen.MapScreen,
        Screen.CompassScreen,
    )

    if (currentUser != null && currentRoute != Screen.Login.route && currentRoute != Screen.Registration.route) {
        BottomNavigation {

            items.forEach { screen ->
                val isSelected = screen.route == currentRoute

                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(id = screen.title)) },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun TopBar(navController: NavHostController, loginViewModel: LoginViewModel) {
    val currentUser = Firebase.auth.currentUser
    val currentRoute = currentRoute(navController)

    TopAppBar(
        title = {
            Text(text = "Mosque App")
        },
        actions = {
            if (currentUser != null && currentRoute != Screen.Login.route) {
                IconButton(
                    onClick = {
                        Firebase.auth.signOut()
                        loginViewModel.currentUserId.value = Resource.Empty()
                        navController.navigate(Screen.Login.route)
                    },
                ) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val backStackEntry = navController.currentBackStackEntryAsState().value
    return backStackEntry?.destination?.route
}
