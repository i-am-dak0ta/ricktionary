package com.dak0ta.ricktionary.feature.home.domain.usecase

import com.dak0ta.ricktionary.core.domain.CharacterDetail

interface GetCharacterByIdUseCase {

    suspend operator fun invoke(id: Int): CharacterDetail
}
