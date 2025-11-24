package com.dak0ta.ricktionary.core.network.data.di

import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchers
import com.dak0ta.ricktionary.core.network.data.api.service.CharactersService
import com.dak0ta.ricktionary.core.network.data.api.service.EpisodesService
import com.dak0ta.ricktionary.core.network.data.converter.CharacterGenderConverter
import com.dak0ta.ricktionary.core.network.data.converter.CharacterStatusConverter
import com.dak0ta.ricktionary.core.network.data.network.ApiLoggingInterceptorProvider
import com.dak0ta.ricktionary.core.network.data.network.SafeApiCall
import com.dak0ta.ricktionary.core.network.data.repository.CharactersRemoteRepositoryImpl
import com.dak0ta.ricktionary.core.network.domain.repository.CharactersRemoteRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val DEFAULT_BASE_URL = "https://rickandmortyapi.com/api/"
    private const val CONNECT_TIMEOUT_SECONDS = 30L
    private const val READ_TIMEOUT_SECONDS = 30L
    private const val WRITE_TIMEOUT_SECONDS = 30L

    @Provides
    @Named("baseUrl")
    @Singleton
    fun provideBaseUrl(): String = System.getProperty("api.baseUrl") ?: DEFAULT_BASE_URL

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(CharacterGenderConverter())
        .add(CharacterStatusConverter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = ApiLoggingInterceptorProvider().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideCourseRemoteRepository(
        retrofit: Retrofit,
        dispatchers: CoroutineDispatchers,
    ): CharactersRemoteRepository {
        val charactersService = retrofit.create(CharactersService::class.java)
        val episodesService = retrofit.create(EpisodesService::class.java)
        val safeApiCall = SafeApiCall(dispatchers)
        return CharactersRemoteRepositoryImpl(charactersService, episodesService, safeApiCall)
    }
}
