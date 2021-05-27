package com.mistpaag.requestmanager.di

import com.mistpaag.requestmanager.API_URL
import com.mistpaag.requestmanager.RetrofitBuild
import com.mistpaag.requestmanager.endpoint.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providesRetrofitBuild(): Retrofit {
        return RetrofitBuild(API_URL).retrofit
    }

    @Provides
    fun providesLocationService(
        retrofit: Retrofit
    ): LocationService {
        return retrofit.create(
            LocationService::class.java
        )
    }
}