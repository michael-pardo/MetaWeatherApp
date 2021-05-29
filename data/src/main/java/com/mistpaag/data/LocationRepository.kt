package com.mistpaag.data

import com.mistpaag.domain.WLocation
import com.mistpaag.domain.info.WLocationInfo
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun searchLocationByQuery(query: String): Flow<ResultsData<List<WLocation>>>
    fun getLocationInfo(woeid: Long): Flow<ResultsData<WLocationInfo>>
}