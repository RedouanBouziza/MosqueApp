package com.example.capstoneredouan.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.capstoneredouan.data.api.PrayerTimesApi
import com.example.capstoneredouan.data.model.PrayerTimes
import androidx.compose.runtime.mutableStateOf
import com.example.capstoneredouan.data.repository.PrayerTimesRepository


class PrayerTimesViewModel : ViewModel() {
    private val repository = PrayerTimesRepository()

    val prayerTimesState = mutableStateOf<PrayerTimes?>(null)
    val dayTimeState = mutableStateOf("")

    private val errorState = mutableStateOf<String?>(null)

    fun fetchPrayerTimes(context: Context, city: String, country: String) {
        repository.fetchPrayerTimes(
            context,
            city,
            country,
            onSuccess = { prayerTimes ->
                prayerTimesState.value = prayerTimes
            },
            onError = { error ->
                handleApiError(error)
            }
        )
    }

    fun fetchDaytime(context: Context, city: String, country: String) {
        repository.fetchDaytime(
            context,
            city,
            country,
            onSuccess = { daytime ->
                dayTimeState.value = daytime
            },
            onError = { error ->
                handleApiError(error)
            }
        )
    }

    private fun handleApiError(error: String) {
        Log.e(TAG, "API error: $error")
        errorState.value = error
    }

    companion object {
        private const val TAG = "PrayerTimesViewModel"
    }
}