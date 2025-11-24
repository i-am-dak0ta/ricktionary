package com.dak0ta.ricktionary.feature.home.domain.usecase

import androidx.paging.PagingData
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import kotlinx.coroutines.flow.Flow

interface GetCharactersPagerUseCase {

    operator fun invoke(): Flow<PagingData<CharacterSummary>>
}
