package com.dak0ta.ricktionary.feature.home.data.di

import com.dak0ta.ricktionary.feature.home.data.repository.CharactersRepository
import com.dak0ta.ricktionary.feature.home.data.repository.CharactersRepositoryImpl
import com.dak0ta.ricktionary.feature.home.data.usecase.GetCharacterByIdUseCaseImpl
import com.dak0ta.ricktionary.feature.home.data.usecase.GetCharactersPagerUseCaseImpl
import com.dak0ta.ricktionary.feature.home.domain.usecase.GetCharacterByIdUseCase
import com.dak0ta.ricktionary.feature.home.domain.usecase.GetCharactersPagerUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class HomeDataModule {

    @Binds
    @Singleton
    internal abstract fun bindCharactersRepository(
        impl: CharactersRepositoryImpl,
    ): CharactersRepository

    @Binds
    internal abstract fun bindGetCharactersUseCase(
        impl: GetCharactersPagerUseCaseImpl,
    ): GetCharactersPagerUseCase

    @Binds
    internal abstract fun bindGetCharacterByIdUseCase(
        impl: GetCharacterByIdUseCaseImpl,
    ): GetCharacterByIdUseCase
}
