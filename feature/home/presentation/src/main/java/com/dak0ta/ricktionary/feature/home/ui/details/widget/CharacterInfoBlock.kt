package com.dak0ta.ricktionary.feature.home.ui.details.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.dak0ta.ricktionary.feature.home.presentation.details.ui.CharacterDetailUi

@Composable
internal fun CharacterInfoBlock(character: CharacterDetailUi) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        InfoLine(character.status)
        InfoLine(character.species)
        InfoLine(character.type)
        InfoLine(character.gender)
        InfoLine("Origin: ${character.origin}")
        InfoLine("Location: ${character.location}")
    }
}
