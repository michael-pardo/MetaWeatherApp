package com.mistpaag.metaweatherapp.parcelables

import com.mistpaag.domain.WLocation

fun WLocationParcelable.toDomain() =
    WLocation(
        title= title,
        locationType= locationType,
        woeid= woeid,
    )

fun WLocation.toParcelable() =
    WLocationParcelable(
        title= title,
        locationType= locationType,
        woeid= woeid,
    )