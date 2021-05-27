package com.mistpaag.requestmanager.di

import com.mistpaag.data.source.LocationRemoteSource
import com.mistpaag.requestmanager.LocationRemoteDataSource
import com.mistpaag.requestmanager.endpoint.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationRemoteModule {

    @Provides
    fun providesPokemonRemoteSource(
        locationService: LocationService
    ): LocationRemoteSource = LocationRemoteDataSource(
        locationService = locationService
    )
}