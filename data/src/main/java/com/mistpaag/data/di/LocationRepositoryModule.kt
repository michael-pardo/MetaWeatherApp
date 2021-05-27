package com.mistpaag.data.di

import com.mistpaag.data.LocationRepository
import com.mistpaag.data.LocationRepositoryImpl
import com.mistpaag.data.source.LocationRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationRepositoryModule {
    @Provides
    fun providesLocationRepository(
        locationRemoteSource: LocationRemoteSource
    ): LocationRepository = LocationRepositoryImpl(
        locationRemoteSource = locationRemoteSource
    )
}