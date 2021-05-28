package com.mistpaag.metaweatherapp.parcelables

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WLocationParcelable(
    val title: String,
    val locationType: String,
    val woeid: Long
): Parcelable