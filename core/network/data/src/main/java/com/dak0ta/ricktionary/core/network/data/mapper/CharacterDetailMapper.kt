package com.dak0ta.ricktionary.core.network.data.mapper

import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.core.network.data.api.dto.CharacterDetailDto
import com.dak0ta.ricktionary.core.network.data.api.dto.EpisodeDto

internal fun CharacterDetailDto.toDomain(episodes: List<EpisodeDto>) = CharacterDetail(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.name,
    location = location.name,
    image = image,
    episodes = episodes.map { it.toDomain() },
)
