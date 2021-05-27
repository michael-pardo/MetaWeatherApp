package com.mistpaag.requestmanager.model


import com.google.gson.annotations.SerializedName

data class WLocationServer(
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Long
)