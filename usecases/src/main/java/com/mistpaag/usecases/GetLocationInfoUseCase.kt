package com.mistpaag.usecases

import com.mistpaag.data.LocationRepository
import com.mistpaag.data.ResultsData
import com.mistpaag.domain.info.WLocationInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationInfoUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    fun invoke(woeid: Long) : Flow<ResultsData<WLocationInfo>>{
        return locationRepository.getLocationInfo(woeid)
    }
}