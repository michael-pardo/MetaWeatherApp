package com.mistpaag.metaweatherapp.presentation.main.detail

import com.mistpaag.domain.info.WLocationInfo

sealed class LocationDetailState {
    data class LocationInfo(val wLocationInfo: WLocationInfo): LocationDetailState()
    data class Error(val message: String): LocationDetailState()
}