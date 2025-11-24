package com.dak0ta.ricktionary.core.network.data.api.service

import com.dak0ta.ricktionary.core.network.data.api.dto.CharacterDetailDto
import com.dak0ta.ricktionary.core.network.data.api.dto.CharactersResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CharactersService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int? = null): CharactersResponseDto

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDetailDto
}
