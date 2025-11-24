package com.dak0ta.ricktionary.core.network.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CharactersResponseInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)
