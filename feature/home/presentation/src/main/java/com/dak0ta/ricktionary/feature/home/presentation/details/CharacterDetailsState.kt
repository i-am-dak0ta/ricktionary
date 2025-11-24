package com.dak0ta.ricktionary.feature.home.presentation.details

import com.dak0ta.ricktionary.core.domain.CharacterDetail

internal sealed interface CharacterDetailsState {

    object Loading : CharacterDetailsState

    data class Content(val character: CharacterDetail) : CharacterDetailsState

    object Error : CharacterDetailsState
}
