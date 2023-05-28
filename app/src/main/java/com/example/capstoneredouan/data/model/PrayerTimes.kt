package com.example.capstoneredouan.data.model

import org.json.JSONObject

data class PrayerTimes(
    val dayTime: String,
    val fajrTime: String,
    val dhuhrTime: String,
    val asrTime: String,
    val maghribTime: String,
    val ishaTime: String
) {
    companion object {
        fun fromJson(json: JSONObject): PrayerTimes {
            val data = json.getJSONObject("data")
            val timings = data.getJSONObject("timings")

            val dayTime = data.getJSONObject("date").getString("readable")
            val fajrTime = timings.getString("Fajr")
            val dhuhrTime = timings.getString("Dhuhr")
            val asrTime = timings.getString("Asr")
            val maghribTime = timings.getString("Maghrib")
            val ishaTime = timings.getString("Isha")

            return PrayerTimes(dayTime, fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime)
        }
    }
}

