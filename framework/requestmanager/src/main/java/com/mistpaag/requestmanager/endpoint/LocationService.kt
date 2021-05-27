package com.mistpaag.requestmanager.endpoint

import com.mistpaag.requestmanager.model.WLocationServer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("location/search")
    suspend fun searchLocation(
        @Query("query") query: String
    ): Response<List<WLocationServer>>
}