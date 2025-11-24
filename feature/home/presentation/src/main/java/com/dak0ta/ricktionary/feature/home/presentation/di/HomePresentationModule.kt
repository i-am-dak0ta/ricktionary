package com.dak0ta.ricktionary.feature.home.presentation.di

import androidx.lifecycle.ViewModel
import com.dak0ta.ricktionary.core.di.ViewModelKey
import com.dak0ta.ricktionary.feature.home.presentation.details.CharacterDetailsViewModel
import com.dak0ta.ricktionary.feature.home.presentation.list.CharacterListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomePresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    internal abstract fun bindCharacterListViewModel(viewModel: CharacterListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    internal abstract fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel
}
