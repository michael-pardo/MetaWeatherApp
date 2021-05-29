package com.mistpaag.usecases.di

import com.mistpaag.data.LocationRepository
import com.mistpaag.usecases.GetLocationInfoUseCase
import com.mistpaag.usecases.SearchLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationUseCaseModule {

    @Provides
    fun provides(
        locationRepository: LocationRepository
    ) = SearchLocationUseCase(
        locationRepository = locationRepository
    )

    @Provides
    fun providesGetLocationInfoUseCase(

    ) = GetLocationInfoUseCase(

    )
}