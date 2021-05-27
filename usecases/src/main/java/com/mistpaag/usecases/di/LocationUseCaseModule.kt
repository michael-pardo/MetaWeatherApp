package com.mistpaag.usecases.di

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

    ) = SearchLocationUseCase(

    )
}