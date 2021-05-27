package com.mistpaag.data.source

import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import kotlinx.coroutines.flow.Flow

interface LocationRemoteSource {
    fun searchLocationByQuery(query: String): Flow<ResultsData<List<WLocation>>>
}