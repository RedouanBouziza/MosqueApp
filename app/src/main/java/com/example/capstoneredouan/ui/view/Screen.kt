package com.example.capstoneredouan.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.capstoneredouan.R

sealed class Screen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object PrayerTimesScreen: Screen("prayer_times_screen", R.string.prayer_times, Icons.Default.WatchLater)
    object MapScreen: Screen("map_screen", R.string.map, Icons.Default.LocationOn)
    object CompassScreen: Screen("compass_screen", R.string.compass, Icons.Default.Explore)
    object Login: Screen("login", R.string.login, Icons.Default.Login)
    object Registration: Screen("registration", R.string.log_out, Icons.Default.HowToReg)
}