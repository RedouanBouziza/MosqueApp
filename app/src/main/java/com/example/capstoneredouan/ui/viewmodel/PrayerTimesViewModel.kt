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

class PrayerTimesViewModel : ViewModel() {
    val prayerTimesState = mutableStateOf<PrayerTimes?>(null)
    val dayTimeState = mutableStateOf("")

    fun fetchPrayerTimes(context: Context, city: String, country: String) {
        val url = PrayerTimesApi.getPrayerTimesUrl(city, country)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val prayerTimes = PrayerTimes.fromJson(response)
                prayerTimesState.value = prayerTimes
            },
            { error ->
                Log.e(TAG, "Error fetching prayer times: ${error.message}")
            })

        Volley.newRequestQueue(context).add(request)
    }

    fun fetchDaytime(context: Context, city: String, country: String) {
        val url = PrayerTimesApi.getPrayerTimesUrl(city, country)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val data = response.getJSONObject("data")
                val daytime = data.getJSONObject("date").getString("readable")
                dayTimeState.value = daytime
            },
            { error ->
                Log.e(TAG, "Error fetching daytime: ${error.message}")
            })

        Volley.newRequestQueue(context).add(request)
    }

    companion object {
        private const val TAG = "PrayerTimesViewModel"
    }
}