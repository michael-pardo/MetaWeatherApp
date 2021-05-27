package com.mistpaag.data

import com.mistpaag.data.source.LocationRemoteSource
import com.mistpaag.domain.WLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteSource: LocationRemoteSource
): LocationRepository {
    override fun searchLocationByQuery(query: String): Flow<ResultsData<List<WLocation>>> {
        return locationRemoteSource.searchLocationByQuery(query)
    }
}