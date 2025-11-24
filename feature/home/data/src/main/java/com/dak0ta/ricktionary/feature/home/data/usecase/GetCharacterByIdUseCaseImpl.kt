package com.dak0ta.ricktionary.feature.home.data.usecase

import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.feature.home.data.repository.CharactersRepository
import com.dak0ta.ricktionary.feature.home.domain.usecase.GetCharacterByIdUseCase
import javax.inject.Inject

internal class GetCharacterByIdUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
) : GetCharacterByIdUseCase {

    override suspend fun invoke(id: Int): CharacterDetail {
        return repository.getCharacterByID(id)
    }
}
