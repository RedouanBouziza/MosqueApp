package com.example.capstoneredouan.ui.view.route

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen("home_screen")
    object PrayerTimesScreen: Screen("prayer_times_screen")
    object MapScreen: Screen("map_screen")
    object CompassScreen: Screen("compass_screen")
    object SignInScreen: Screen("sign_in_screen")
    object ForgotPasswordScreen: Screen("forgot_password_screen")
    object SignUpScreen: Screen("sign_up_screen")
    object VerifyEmailScreen: Screen("verify_email_screen")
    object ProfileScreen: Screen("profile_screen")
}