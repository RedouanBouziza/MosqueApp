package com.example.capstoneredouan.ui.view

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen("home_screen")
    object PrayerTimesScreen: Screen("prayer_times_screen")
    object MapScreen: Screen("map_screen")
    object CompassScreen: Screen("compass_screen")
    object Login: Screen("login")
    object Registration: Screen("registration")
}