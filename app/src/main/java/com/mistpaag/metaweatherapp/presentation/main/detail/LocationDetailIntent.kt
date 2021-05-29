package com.mistpaag.metaweatherapp.presentation.main.detail

import com.mistpaag.domain.WLocation

sealed class LocationDetailIntent {
    data class GetLocationInfo(val wLocation: WLocation): LocationDetailIntent()
}