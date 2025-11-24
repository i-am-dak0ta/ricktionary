package com.dak0ta.ricktionary.core.network.data.mapper

import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.core.network.data.api.dto.CharacterSummaryDto

internal fun CharacterSummaryDto.toDomain(): CharacterSummary = CharacterSummary(
    id = id,
    name = name,
    status = status,
    species = species,
    image = image,
)
