package com.dak0ta.ricktionary.core.network.data.converter

import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class CharacterStatusConverter {

    @FromJson
    fun fromJson(value: String): CharacterStatus = when (value) {
        "Alive" -> CharacterStatus.ALIVE
        "Dead" -> CharacterStatus.DEAD
        else -> CharacterStatus.UNKNOWN
    }

    @ToJson
    fun toJson(value: CharacterStatus): String = when (value) {
        CharacterStatus.ALIVE -> "Alive"
        CharacterStatus.DEAD -> "Dead"
        CharacterStatus.UNKNOWN -> "Unknown"
    }
}
