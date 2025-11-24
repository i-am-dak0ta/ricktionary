package com.dak0ta.ricktionary.core.network.data.api.service

import com.dak0ta.ricktionary.core.network.data.api.dto.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface EpisodesService {

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): EpisodeDto

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(@Path("ids") ids: String): List<EpisodeDto>
}
