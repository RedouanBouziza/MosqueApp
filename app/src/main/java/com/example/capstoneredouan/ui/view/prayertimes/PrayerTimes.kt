package com.example.capstoneredouan.ui.view.prayertimes

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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

    var hasFetchedPrayerTimes by remember { mutableStateOf(false) }

    viewModel.fetchDaytime(context, city, country)

    if (!hasFetchedPrayerTimes) {
        viewModel.fetchPrayerTimes(context, city, country)
        hasFetchedPrayerTimes = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.mosque_green_background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(668.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(R.color.white)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.prayer_times),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.padding(top = 10.dp)
                )

                val dayTime = viewModel.dayTimeState.value

                Text(
                    text = dayTime,
                    style = MaterialTheme.typography.h5,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .background(colorResource(R.color.white)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .background(colorResource(R.color.light_gray)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val prayerTimes = viewModel.prayerTimesState.value

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

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = city,
                    onValueChange = { city = it },
                    placeholder = { Text(text = stringResource(id = R.string.city)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        placeholderColor = MaterialTheme.colors.onSurface,
                        cursorColor = Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = country,
                    onValueChange = { country = it },
                    placeholder = { Text(text = stringResource(id = R.string.country)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colorResource(id = R.color.light_gray),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        placeholderColor = MaterialTheme.colors.onSurface,
                        cursorColor = Color.Black,
                    )
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(55.dp),
                    onClick = {
                        if (city.isNotEmpty() && country.isNotEmpty()) {
                            viewModel.fetchPrayerTimes(context, city, country)
                        }
                    },
                ) {
                    Text(text = stringResource(id = R.string.update))
                }
            }
        }
    }
}