package com.dak0ta.ricktionary.core.network.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationNameDto(
    val name: String,
)
