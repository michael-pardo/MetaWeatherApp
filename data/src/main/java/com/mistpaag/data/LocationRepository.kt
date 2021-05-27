package com.mistpaag.data

import com.mistpaag.domain.WLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun searchLocationByQuery(query: String): Flow<ResultsData<List<WLocation>>>
}