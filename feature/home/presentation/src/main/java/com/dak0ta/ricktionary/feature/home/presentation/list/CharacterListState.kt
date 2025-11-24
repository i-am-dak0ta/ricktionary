package com.dak0ta.ricktionary.feature.home.presentation.list

import androidx.paging.PagingData
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import kotlinx.coroutines.flow.Flow

internal sealed interface CharacterListState {

    object Loading : CharacterListState

    data class Content(val characters: Flow<PagingData<CharacterSummary>>) : CharacterListState

    object Error : CharacterListState
}
