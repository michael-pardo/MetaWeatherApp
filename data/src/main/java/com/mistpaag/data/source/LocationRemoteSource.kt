package com.mistpaag.data.source

import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import com.mistpaag.domain.info.WLocationInfo
import kotlinx.coroutines.flow.Flow

interface LocationRemoteSource {
    fun searchLocationByQuery(query: String): Flow<ResultsData<List<WLocation>>>
    fun getLocationInfo(woeid: Long): Flow<ResultsData<WLocationInfo>>
}