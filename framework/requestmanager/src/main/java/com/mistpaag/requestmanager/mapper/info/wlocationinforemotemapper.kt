package com.mistpaag.requestmanager.mapper.info

import com.mistpaag.domain.info.WLocationInfo
import com.mistpaag.requestmanager.model.info.WLocationInfoServer

fun WLocationInfoServer.toDomain() =
    WLocationInfo(
        title= title,
        locationType= locationType,
        woeid= woeid,
        time= time,
        sunRise= sunRise,
        sunSet= sunSet,
        timezone= timezoneName,
        consolidatedWeatherList= consolidatedWeatherServerList.toDomain(),
    )