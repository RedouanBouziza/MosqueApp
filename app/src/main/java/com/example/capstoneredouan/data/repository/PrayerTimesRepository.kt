package com.example.capstoneredouan.data.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.capstoneredouan.data.api.PrayerTimesApi
import com.example.capstoneredouan.data.model.PrayerTimes

class PrayerTimesRepository {

    fun fetchPrayerTimes(
        context: Context,
        city: String,
        country: String,
        onSuccess: (PrayerTimes) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = PrayerTimesApi.getPrayerTimesUrl(city, country)

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val prayerTimes = PrayerTimes.fromJson(response)
                onSuccess(prayerTimes)
            },
            { error ->
                onError("Error fetching prayer times: ${error.message}")
            })

        Volley.newRequestQueue(context).add(request)
    }

    fun fetchDaytime(
        context: Context,
        city: String,
        country: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = PrayerTimesApi.getPrayerTimesUrl(city, country)

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val data = response.getJSONObject("data")
                val daytime = data.getJSONObject("date").getString("readable")
                onSuccess(daytime)
            },
            { error ->
                onError("Error fetching daytime: ${error.message}")
            })

        Volley.newRequestQueue(context).add(request)
    }
}
