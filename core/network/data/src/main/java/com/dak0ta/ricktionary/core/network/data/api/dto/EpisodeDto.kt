package com.dak0ta.ricktionary.core.network.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class EpisodeDto(
    val id: Int,
    val name: String,
    val episode: String,
)
