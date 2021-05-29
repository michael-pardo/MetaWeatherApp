package com.mistpaag.requestmanager.model.info


import com.google.gson.annotations.SerializedName

data class ParentServer(
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Int
)