package com.mistpaag.metaweatherapp.presentation.main.search

sealed class SearchLocationIntent {
    data class SearchLocation(val query: String): SearchLocationIntent()
}