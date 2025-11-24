package com.dak0ta.ricktionary.core.network.data.api.dto

import com.dak0ta.ricktionary.core.domain.CharacterGender
import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CharacterDetailDto(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val origin: LocationNameDto,
    val location: LocationNameDto,
    val image: String,
    val episode: List<String>,
)
