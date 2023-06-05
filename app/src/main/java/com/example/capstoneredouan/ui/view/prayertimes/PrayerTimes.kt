package com.example.capstoneredouan.ui.view.prayertimes

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.capstoneredouan.R
import com.example.capstoneredouan.data.api.PrayerTimesApi.context
import com.example.capstoneredouan.ui.viewmodel.LoginViewModel
import com.example.capstoneredouan.ui.viewmodel.PrayerTimesViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun PrayerTimes(
    viewModel: PrayerTimesViewModel,
) {
    var city by remember { mutableStateOf("Amsterdam") }
    var country by remember { mutableStateOf("Netherlands") }
    val image: Painter = painterResource(id = R.drawable.img)

    viewModel.fetchDaytime(context, city, country)
    viewModel.fetchPrayerTimes(context, city, country)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.mosque_green_background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Display prayer times from the ViewModel
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(280.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val prayerTimes = viewModel.prayerTimesState.value
                val dayTime = viewModel.dayTimeState.value

                Text(
                    text = dayTime,
                    style = MaterialTheme.typography.h5,
//                fontWeight = FontWeight.Bold,
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

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(10.dp)),
                value = city,
                onValueChange = { city = it },
                label = { Text(text = stringResource(id = R.string.city)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                value = country,
                onValueChange = { country = it },
                label = { Text(text = stringResource(id = R.string.country)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
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
        }
    }
}