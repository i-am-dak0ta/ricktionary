package com.dak0ta.ricktionary.core.network.data.repository

import androidx.paging.PagingSource
import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.core.network.data.api.service.CharactersService
import com.dak0ta.ricktionary.core.network.data.api.service.EpisodesService
import com.dak0ta.ricktionary.core.network.data.mapper.toDomain
import com.dak0ta.ricktionary.core.network.data.network.SafeApiCall
import com.dak0ta.ricktionary.core.network.domain.model.ApiResult
import com.dak0ta.ricktionary.core.network.domain.repository.CharactersRemoteRepository

internal class CharactersRemoteRepositoryImpl(
    private val charactersService: CharactersService,
    private val episodesService: EpisodesService,
    private val safeApiCall: SafeApiCall,
) : CharactersRemoteRepository {

    override fun getCharactersPage(): PagingSource<Int, CharacterSummary> =
        CharactersPagingSource(charactersService, safeApiCall)

    override suspend fun getCharacterById(id: Int): ApiResult<CharacterDetail> {
        return safeApiCall("GET_CHARACTER_BY_ID") {
            val characterDetailDto = charactersService.getCharacterById(id)
            val episodesIds = characterDetailDto.episode
                .map { it.substringAfterLast("/").toInt() }
            val episodesDto = when {
                episodesIds.isEmpty() -> emptyList()
                episodesIds.size == 1 -> listOf(episodesService.getEpisodeById(episodesIds.first()))
                else -> episodesService.getEpisodesByIds(episodesIds.joinToString(","))
            }
            characterDetailDto.toDomain(episodesDto)
        }
    }
}
