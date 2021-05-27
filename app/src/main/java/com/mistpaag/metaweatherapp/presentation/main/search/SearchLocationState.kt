package com.mistpaag.metaweatherapp.presentation.main.search

import com.mistpaag.domain.WLocation

sealed class SearchLocationState {
    data class SearchList(val list: List<WLocation>): SearchLocationState()
    data class Error(val message: String): SearchLocationState()
    object Loading: SearchLocationState()
}