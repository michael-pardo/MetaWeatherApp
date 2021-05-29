package com.mistpaag.requestmanager.model.info


import com.google.gson.annotations.SerializedName

data class WLocationInfoServer(
    @SerializedName("consolidated_weather")
    val consolidatedWeatherServerList: List<ConsolidatedWeatherServer>,
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("parent")
    val parentServer: ParentServer,
    @SerializedName("sources")
    val sourceServers: List<SourceServer>,
    @SerializedName("sun_rise")
    val sunRise: String,
    @SerializedName("sun_set")
    val sunSet: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_name")
    val timezoneName: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Long
)