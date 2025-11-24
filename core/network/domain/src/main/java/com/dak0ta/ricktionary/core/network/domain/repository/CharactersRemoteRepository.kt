package com.dak0ta.ricktionary.core.network.domain.repository

import androidx.paging.PagingSource
import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.core.network.domain.model.ApiResult

interface CharactersRemoteRepository {

    fun getCharactersPage(): PagingSource<Int, CharacterSummary>
    suspend fun getCharacterById(id: Int): ApiResult<CharacterDetail>
}
