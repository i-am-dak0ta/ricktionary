package com.dak0ta.ricktionary.core.network.data.network

import com.dak0ta.ricktionary.core.network.data.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Singleton
internal class ApiLoggingInterceptorProvider {

    fun create(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }
}
