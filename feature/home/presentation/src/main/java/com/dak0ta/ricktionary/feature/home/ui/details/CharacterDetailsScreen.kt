package com.dak0ta.ricktionary.feature.home.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dak0ta.ricktionary.core.navigation.compose.LocalNavController
import com.dak0ta.ricktionary.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.ricktionary.feature.home.presentation.details.CharacterDetailsAction
import com.dak0ta.ricktionary.feature.home.presentation.details.CharacterDetailsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun CharacterDetailsScreen() {
    val viewModelFactory = LocalViewModelFactory.current
    val viewModel: CharacterDetailsViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    val characterId = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Int>("characterId")

    println(characterId)

    LaunchedEffect(Unit) {
        viewModel.initialize()
        characterId?.let { viewModel.initWithId(it) }

        viewModel.action
            .onEach { action ->
                when (action) {
                    is CharacterDetailsAction.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
            .launchIn(this)
    }

    CharacterDetailsScreenContent(
        state = state,
        onBackClick = viewModel::onBackClick,
        onRetryClick = viewModel::onRetryClick,
    )
}
