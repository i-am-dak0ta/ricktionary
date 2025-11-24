package com.dak0ta.ricktionary.core.coroutine.di

import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchers
import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchersImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class CoroutineModule {

    @Binds
    @Singleton
    abstract fun bindCoroutineDispatchers(
        impl: CoroutineDispatchersImpl
    ): CoroutineDispatchers
}
