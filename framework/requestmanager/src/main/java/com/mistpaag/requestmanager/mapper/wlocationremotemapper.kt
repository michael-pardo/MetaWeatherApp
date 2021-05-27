package com.mistpaag.requestmanager.mapper

import com.mistpaag.domain.WLocation
import com.mistpaag.requestmanager.model.WLocationServer

fun WLocationServer.toDomain() =
    WLocation(
        title= title,
        locationType= locationType,
        woeid= woeid,
    )

fun List<WLocationServer>.toDomain() =
    this.map { it.toDomain() }