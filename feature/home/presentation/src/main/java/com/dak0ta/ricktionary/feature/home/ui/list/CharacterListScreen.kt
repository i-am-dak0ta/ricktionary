package com.dak0ta.ricktionary.feature.home.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dak0ta.ricktionary.core.navigation.compose.LocalNavController
import com.dak0ta.ricktionary.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.ricktionary.core.navigation.compose.navigateTo
import com.dak0ta.ricktionary.feature.home.presentation.list.CharacterListAction
import com.dak0ta.ricktionary.feature.home.presentation.list.CharacterListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun CharacterListScreen() {
    val viewModelFactory = LocalViewModelFactory.current
    val viewModel: CharacterListViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.initialize()

        viewModel.action
            .onEach { action ->
                when (action) {
                    is CharacterListAction.NavigateTo -> {
                        navController.navigateTo(action.directionClass)
                        navController.currentBackStackEntry?.savedStateHandle?.set("characterId", action.characterId)
                    }
                }
            }
            .launchIn(this)
    }

    CharacterListScreenContent(
        state = state,
        onCharacterClick = viewModel::onCharacterClick,
        onRetryClick = viewModel::onRetryClick,
    )
}
