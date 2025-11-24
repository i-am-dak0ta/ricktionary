package com.dak0ta.ricktionary.core.network.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CharactersResponseDto(
    val info: CharactersResponseInfoDto,
    val results: List<CharacterSummaryDto>,
)
