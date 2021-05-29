package com.mistpaag.metaweatherapp.utils

import android.annotation.SuppressLint
import com.mistpaag.domain.info.WLocationInfo
import java.text.SimpleDateFormat
import java.util.*

fun Double.toTemperatureText() =
    this.toInt().toString()

@SuppressLint("SimpleDateFormat")
private fun format(str: String, timezone: String): String {
    try {
        val slits = str.split(".")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        inputFormat.timeZone = TimeZone.getTimeZone(timezone/*"Europe/London"*/)

        val outputFormat = SimpleDateFormat("h:mm a")
        outputFormat.timeZone = inputFormat.timeZone
        return outputFormat.format(inputFormat.parse(slits[0])!!)
    } catch (e: Exception) {

    }
    return "-"
}

fun WLocationInfo.formattedTime(): String {
    return format(time, timezone)
}

fun WLocationInfo.formattedSunriseTime(): String {
    return format(sunRise, timezone)
}

fun WLocationInfo.formattedSunsetTime(): String {
    return format(sunSet, timezone)
}