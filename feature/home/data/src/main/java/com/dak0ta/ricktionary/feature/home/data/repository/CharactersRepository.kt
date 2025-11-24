package com.dak0ta.ricktionary.feature.home.data.repository

import androidx.paging.PagingData
import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import kotlinx.coroutines.flow.Flow

internal interface CharactersRepository {

    fun getCharactersPager(): Flow<PagingData<CharacterSummary>>
    suspend fun getCharacterByID(id: Int): CharacterDetail
}
