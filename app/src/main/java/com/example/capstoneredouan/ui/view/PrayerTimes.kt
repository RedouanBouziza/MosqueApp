package com.example.capstoneredouan.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.capstoneredouan.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PrayerTimes() {
    var dayTime by remember { mutableStateOf("") }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.mosque_green_background))
    ) {
        // Display all prayer times in a column
        Column(
            modifier = Modifier
                .padding(16.dp)
                .height(300.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayTime,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.fajr, fajrTime),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.dhuhr, dhuhrTime),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.asr, asrTime),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.maghrib, maghribTime),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.isha, ishaTime),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )


//        Text(text = "Sunrise: $sunriseTime")
//        Text(text = "Dhuhr: $dhuhrTime")
//        Text(text = "Asr: $asrTime")
//        Text(text = "Sunset: $sunsetTime")
//        Text(text = "Maghrib: $maghribTime")
//        Text(text = "Isha: $ishaTime")
//        Text(text = "Imsak: $imsakTime")
//        Text(text = "Midnight: $midnightTime")
        }
    }


    LaunchedEffect(Unit) {
        // Make the network request to retrieve prayer times
        val url =
            "https://api.aladhan.com/v1/timingsByCity?city=Amsterdam&country=Netherlands&method=8"
        withContext(Dispatchers.IO) {
            try {
                val requestQueue = Volley.newRequestQueue(context)
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        // Handle successful response data
                        val data = response.getJSONObject("data")
                        val timings = data.getJSONObject("timings")

                        // Extract the readable date text
                        val date = data.getJSONObject("date")
                        val readableDate = date.getString("readable")
                        dayTime = readableDate

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
}