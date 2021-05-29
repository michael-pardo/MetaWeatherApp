package com.mistpaag.metaweatherapp.presentation.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import com.mistpaag.usecases.GetLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val getLocationInfoUseCase: GetLocationInfoUseCase
): ViewModel() {
    private val _state= MutableLiveData<LocationDetailState>()
    val state: LiveData<LocationDetailState>
        get() = _state

    fun setIntentEvent(locationDetailIntent: LocationDetailIntent){
        viewModelScope.launch {
            when(locationDetailIntent){
                is LocationDetailIntent.GetLocationInfo -> getLocationInfo(locationDetailIntent.wLocation)
            }
        }
    }

    private fun getLocationInfo(wLocation: WLocation){
        viewModelScope.launch {
            getLocationInfoUseCase.invoke(wLocation.woeid).collect { wLocationInfoData ->
                when(wLocationInfoData){
                    is ResultsData.Error -> _state.postValue(LocationDetailState.Error(wLocationInfoData.message))
                    is ResultsData.Success -> _state.postValue(LocationDetailState.LocationInfo(wLocationInfoData.data))
                }
            }
        }
    }
}