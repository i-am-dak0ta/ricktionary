package com.dak0ta.ricktionary.feature.home.presentation.list

import com.dak0ta.ricktionary.core.navigation.Direction
import kotlin.reflect.KClass

internal sealed interface CharacterListAction {

    data class NavigateTo(val directionClass: KClass<out Direction>, val characterId: Int) : CharacterListAction
}
