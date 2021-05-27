package com.mistpaag.metaweatherapp.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistpaag.data.ResultsData
import com.mistpaag.usecases.SearchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val searchLocationUseCase: SearchLocationUseCase
): ViewModel() {

    private val _state= MutableLiveData<SearchLocationState>()
    val state: LiveData<SearchLocationState>
        get() = _state

    fun setIntentEvent(searchLocalIntent: SearchLocationIntent){
        viewModelScope.launch {
            when(searchLocalIntent){
                is SearchLocationIntent.SearchLocation -> searchLocationByQuery(searchLocalIntent.query)
            }
        }
    }

    private fun searchLocationByQuery(query: String){
        if (query.isEmpty()) return
        viewModelScope.launch {
            _state.postValue(SearchLocationState.Loading)
            searchLocationUseCase.invoke(query).collect { wLocationData ->
                when(wLocationData){
                    is ResultsData.Error -> _state.postValue(SearchLocationState.Error(wLocationData.message))
                    is ResultsData.Success -> _state.postValue(SearchLocationState.SearchList(wLocationData.data))
                }
            }
        }
    }
}