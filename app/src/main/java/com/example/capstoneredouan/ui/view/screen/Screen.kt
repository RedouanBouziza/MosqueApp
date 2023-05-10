package com.example.capstoneredouan.ui.view.screen

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen("home_screen")
    object PrayerScreen: Screen("prayer_screen")
    object MapScreen: Screen("map_screen")
    object CompassScreen: Screen("compass_screen")
}