package com.dak0ta.ricktionary.core.network.data.converter

import com.dak0ta.ricktionary.core.domain.CharacterGender
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class CharacterGenderConverter {

    @FromJson
    fun fromJson(value: String): CharacterGender = when (value) {
        "Male" -> CharacterGender.MALE
        "Female" -> CharacterGender.FEMALE
        "Genderless" -> CharacterGender.GENDERLESS
        else -> CharacterGender.UNKNOWN
    }

    @ToJson
    fun toJson(value: CharacterGender): String = when (value) {
        CharacterGender.MALE -> "Male"
        CharacterGender.FEMALE -> "Female"
        CharacterGender.GENDERLESS -> "Genderless"
        CharacterGender.UNKNOWN -> "Unknown"
    }
}
