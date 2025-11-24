package com.dak0ta.ricktionary.core.network.data.api.dto

import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CharacterSummaryDto(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val image: String,
)
