package com.dak0ta.ricktionary.core.domain

data class CharacterSummary(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val image: String
)
