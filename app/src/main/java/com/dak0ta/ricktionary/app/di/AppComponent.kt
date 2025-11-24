package com.dak0ta.ricktionary.app.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.dak0ta.ricktionary.core.coroutine.di.CoroutineModule
import com.dak0ta.ricktionary.core.di.ViewModelFactoryModule
import com.dak0ta.ricktionary.core.network.data.di.NetworkModule
import com.dak0ta.ricktionary.feature.home.data.di.HomeDataModule
import com.dak0ta.ricktionary.feature.home.presentation.di.HomePresentationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CoroutineModule::class,
        NetworkModule::class,
        HomeDataModule::class,
        HomePresentationModule::class,
        ViewModelFactoryModule::class,
    ],
)
interface AppComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
        ): AppComponent
    }
}
