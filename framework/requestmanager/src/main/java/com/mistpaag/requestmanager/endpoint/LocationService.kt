package com.mistpaag.requestmanager.endpoint

import com.mistpaag.requestmanager.model.WLocationServer
import com.mistpaag.requestmanager.model.info.WLocationInfoServer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationService {
    @GET("location/search")
    suspend fun searchLocation(
        @Query("query") query: String
    ): Response<List<WLocationServer>>

    @GET("location/{woeid}")
    suspend fun getLocationInfo(
        @Path("woeid") woeid: Long
    ): Response<WLocationInfoServer>
}