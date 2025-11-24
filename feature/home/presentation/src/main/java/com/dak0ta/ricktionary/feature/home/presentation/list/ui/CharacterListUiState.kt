package com.dak0ta.ricktionary.feature.home.presentation.list.ui

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

internal sealed interface CharacterListUiState {

    object Loading : CharacterListUiState

    data class Content(
        val title: String,
        val characters: Flow<PagingData<CharacterSummaryUi>>,
        val isRefreshing: Boolean = false,
    ) : CharacterListUiState

    data class Error(
        val title: String,
        val description: String,
        val retryButtonText: String,
    ) : CharacterListUiState
}
