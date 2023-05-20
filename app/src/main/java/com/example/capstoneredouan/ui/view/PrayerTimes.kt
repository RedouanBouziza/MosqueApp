package com.example.capstoneredouan.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PrayerTimes() {
    var fajrTime by remember { mutableStateOf("") }
    var sunriseTime by remember { mutableStateOf("") }
    var dhuhrTime by remember { mutableStateOf("") }
    var asrTime by remember { mutableStateOf("") }
    var sunsetTime by remember { mutableStateOf("") }
    var maghribTime by remember { mutableStateOf("") }
    var ishaTime by remember { mutableStateOf("") }
    var imsakTime by remember { mutableStateOf("") }
    var midnightTime by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Make the network request to retrieve prayer times
        val url = "https://api.aladhan.com/v1/timingsByCity?city=Amsterdam&country=Netherlands&method=8"
        withContext(Dispatchers.IO) {
            try {
                val requestQueue = Volley.newRequestQueue(context)
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        // Handle successful response data
                        val data = response.getJSONObject("data")
                        val timings = data.getJSONObject("timings")
                        fajrTime = timings.getString("Fajr")
                        sunriseTime = timings.getString("Sunrise")
                        dhuhrTime = timings.getString("Dhuhr")
                        asrTime = timings.getString("Asr")
                        sunsetTime = timings.getString("Sunset")
                        maghribTime = timings.getString("Maghrib")
                        ishaTime = timings.getString("Isha")
                        imsakTime = timings.getString("Imsak")
                        midnightTime = timings.getString("Midnight")
                    },
                    { error ->
                        // Handle error
                        Log.e("LoadDataError", "Error: ${error.message}")
                    }
                )
                requestQueue.add(jsonObjectRequest)
            } catch (e: Exception) {
                // Handle exception
                Log.e("LoadDataException", "Exception: ${e.message}")
            }
        }
    }

    // Display all prayer times in a column
    Column {
        Text(text = "Fajr: $fajrTime")
//        Text(text = "Sunrise: $sunriseTime")
        Text(text = "Dhuhr: $dhuhrTime")
        Text(text = "Asr: $asrTime")
//        Text(text = "Sunset: $sunsetTime")
        Text(text = "Maghrib: $maghribTime")
        Text(text = "Isha: $ishaTime")
//        Text(text = "Imsak: $imsakTime")
//        Text(text = "Midnight: $midnightTime")
    }
}