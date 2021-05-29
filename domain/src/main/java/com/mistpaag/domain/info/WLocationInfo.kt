package com.mistpaag.domain.info

data class WLocationInfo(
    val title: String,
    val locationType: String,
    val woeid: Long,
    val time: String,
    val sunRise: String,
    val sunSet: String,
    val timezone: String,
    val consolidatedWeatherList: List<ConsolidatedWeather>
)