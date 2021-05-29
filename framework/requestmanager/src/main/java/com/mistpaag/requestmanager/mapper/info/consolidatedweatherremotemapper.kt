package com.mistpaag.requestmanager.mapper.info

import com.mistpaag.domain.info.ConsolidatedWeather
import com.mistpaag.requestmanager.model.info.ConsolidatedWeatherServer

fun ConsolidatedWeatherServer.toDomain() =
    ConsolidatedWeather(
        id=id,
        weatherStateName=weatherStateName,
        applicableDate=applicableDate,
        minTemp=minTemp,
        maxTemp=maxTemp,
        theTemp=theTemp,
        weatherStateAbbr=weatherStateAbbr,
    )

fun List<ConsolidatedWeatherServer>.toDomain() =
    this.map { it.toDomain() }