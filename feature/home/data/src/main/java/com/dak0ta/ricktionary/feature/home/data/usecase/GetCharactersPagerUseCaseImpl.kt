package com.dak0ta.ricktionary.feature.home.data.usecase

import androidx.paging.PagingData
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.feature.home.data.repository.CharactersRepository
import com.dak0ta.ricktionary.feature.home.domain.usecase.GetCharactersPagerUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCharactersPagerUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
) : GetCharactersPagerUseCase {

    override fun invoke(): Flow<PagingData<CharacterSummary>> {
        return repository.getCharactersPager()
    }
}
