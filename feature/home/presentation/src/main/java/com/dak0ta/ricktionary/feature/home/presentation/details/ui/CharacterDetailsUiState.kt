package com.dak0ta.ricktionary.feature.home.presentation.details.ui

internal sealed interface CharacterDetailsUiState {

    object Loading : CharacterDetailsUiState

    data class Content(
        val title: String,
        val character: CharacterDetailUi,
    ) : CharacterDetailsUiState

    data class Error(
        val title: String,
        val description: String,
        val retryButtonText: String,
    ) : CharacterDetailsUiState
}
