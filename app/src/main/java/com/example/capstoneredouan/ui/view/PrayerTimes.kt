package com.example.capstoneredouan.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.capstoneredouan.R
import com.example.capstoneredouan.data.api.PrayerTimesApi
import com.example.capstoneredouan.data.utils.Resource
import com.example.capstoneredouan.ui.view.route.Screen
import com.example.capstoneredouan.ui.viewmodel.LoginViewModel
import com.example.capstoneredouan.ui.viewmodel.PrayerTimesViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("UnrememberedMutableState")
@Composable
fun PrayerTimes(navController: NavHostController, viewModel: PrayerTimesViewModel, loginViewModel: LoginViewModel) {

    val context = LocalContext.current

    var city by remember { mutableStateOf("Amsterdam") }
    var country by remember { mutableStateOf("Netherlands") }

    viewModel.fetchDaytime(context, city, country)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.mosque_green_background))
    ) {

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        TextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )

        Button(
            onClick = {
                if (city.isNotEmpty() && country.isNotEmpty()) {
                    viewModel.fetchPrayerTimes(context, city, country)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Refresh")
        }

        //TODO: Add a button to go to the map

        // Display prayer times from the ViewModel
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
            val prayerTimes = viewModel.prayerTimesState.value
            val dayTime = viewModel.dayTimeState.value

            Text(
                text = viewModel.dayTimeState.value,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
            )

            prayerTimes?.let {
                Text(
                    text = stringResource(id = R.string.fajr, it.fajrTime),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(id = R.string.dhuhr, it.dhuhrTime),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(id = R.string.asr, it.asrTime),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(id = R.string.maghrib, it.maghribTime),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(id = R.string.isha, it.ishaTime),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}