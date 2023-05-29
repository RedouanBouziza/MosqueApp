package com.example.capstoneredouan.data.api

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object PrayerTimesApi {
    lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context
    }

    fun getPrayerTimesUrl(city: String, country: String): String {
        val method = "8"
        val baseUrl = "https://api.aladhan.com/v1/timingsByCity"
        return "$baseUrl?city=$city&country=$country&method=$method"
    }
}

