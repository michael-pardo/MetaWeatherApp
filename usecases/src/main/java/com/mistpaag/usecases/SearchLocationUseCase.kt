package com.mistpaag.usecases

import com.mistpaag.data.LocationRepository
import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    fun invoke(query: String): Flow<ResultsData<List<WLocation>>>{
        return locationRepository.searchLocationByQuery(query)
    }
}