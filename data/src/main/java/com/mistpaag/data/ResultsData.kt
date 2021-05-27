package com.mistpaag.data

sealed class ResultsData<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultsData<T>()
    data class Error(val message: String) : ResultsData<Nothing>()
}