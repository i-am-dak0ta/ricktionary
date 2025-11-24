package com.dak0ta.ricktionary.core.domain

data class CharacterDetail(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val origin: String,
    val location: String,
    val image: String,
    val episodes: List<Episode>,
)
