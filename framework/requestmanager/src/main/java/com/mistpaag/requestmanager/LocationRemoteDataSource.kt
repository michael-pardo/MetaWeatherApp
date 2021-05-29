package com.mistpaag.requestmanager

import com.mistpaag.data.ResultsData
import com.mistpaag.data.source.LocationRemoteSource
import com.mistpaag.domain.WLocation
import com.mistpaag.domain.info.WLocationInfo
import com.mistpaag.requestmanager.endpoint.LocationService
import com.mistpaag.requestmanager.mapper.toDomain
import com.mistpaag.requestmanager.model.WLocationServer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(
    private val locationService: LocationService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): LocationRemoteSource{
    private val errorMessage = "Something failed calling service"
    override fun searchLocationByQuery(query: String): Flow<ResultsData<List<WLocation>>> = flow {
        emit(
            renderSearchLocationData(
                safeApiCall(
                    call = {
                        locationService.searchLocation(query).callServices()
                    },
                    errorMessage = errorMessage
                )
            )
        )
    }.flowOn(dispatcher)

    override fun getLocationInfo(woeid: Long): Flow<ResultsData<WLocationInfo>> {
        TODO("Not yet implemented")
    }

    private fun renderSearchLocationData(responseData: ResultsData<List<WLocationServer>>): ResultsData<List<WLocation>>{
        return when(responseData){
            is ResultsData.Error -> ResultsData.Error(responseData.message)
            is ResultsData.Success -> ResultsData.Success(responseData.data.toDomain())
        }
    }
}