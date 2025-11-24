package com.dak0ta.ricktionary.app.di

import android.app.Application
import android.content.Context

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            application = this
        )
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }
