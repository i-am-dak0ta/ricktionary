package com.dak0ta.ricktionary.feature.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import com.dak0ta.ricktionary.core.navigation.compose.composableDirection
import com.dak0ta.ricktionary.core.navigation.compose.navigationDirection
import com.dak0ta.ricktionary.feature.home.domain.navigation.HomeDirection
import com.dak0ta.ricktionary.feature.home.ui.details.CharacterDetailsScreen
import com.dak0ta.ricktionary.feature.home.ui.list.CharacterListScreen

fun NavGraphBuilder.navigationHome() {
    navigationDirection<HomeDirection>(
        start = CharacterListDirection::class,
    ) {
        composableDirection<CharacterListDirection> {
            CharacterListScreen()
        }
        composableDirection<CharacterDetailsDirection> {
            CharacterDetailsScreen()
        }
    }
}
