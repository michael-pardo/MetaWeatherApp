package com.mistpaag.domain.info

data class ConsolidatedWeather(
    val id: Long,
    val weatherStateName: String,
    val applicableDate: String,
    val minTemp: Double,
    val maxTemp: Double,
    val theTemp: Double,
    val weatherStateAbbr: String,
)