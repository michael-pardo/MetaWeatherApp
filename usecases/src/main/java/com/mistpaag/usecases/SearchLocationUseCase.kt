package com.mistpaag.usecases

import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import kotlinx.coroutines.flow.Flow

class SearchLocationUseCase {
    fun invoke(query: String): Flow<ResultsData<List<WLocation>>>{
        TODO("")
    }
}