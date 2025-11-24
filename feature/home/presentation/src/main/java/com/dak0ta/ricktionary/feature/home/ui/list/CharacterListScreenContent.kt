package com.dak0ta.ricktionary.feature.home.ui.list

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dak0ta.ricktionary.core.design.ErrorScreen
import com.dak0ta.ricktionary.core.design.LoadingScreen
import com.dak0ta.ricktionary.feature.home.presentation.list.ui.CharacterListUiState
import com.dak0ta.ricktionary.feature.home.ui.list.widget.CharacterList
import com.dak0ta.ricktionary.feature.home.ui.test.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreenContent(
    state: CharacterListUiState,
    onCharacterClick: (Int) -> Unit,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is CharacterListUiState.Loading -> {
            LoadingScreen()
        }

        is CharacterListUiState.Content -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        windowInsets = WindowInsets(0.dp),
                        title = {
                            Text(
                                text = state.title,
                                style = MaterialTheme.typography.headlineLarge,
                            )
                        },
                    )
                },
            ) { innerPadding ->
                CharacterList(
                    state = state,
                    onCharacterClick = onCharacterClick,
                    modifier = Modifier
                        .padding(innerPadding)
                        .testTag(TestTags.CHARACTER_LIST_SCREEN),
                )
            }
        }

        is CharacterListUiState.Error -> {
            ErrorScreen(
                title = state.title,
                description = state.description,
                retryButtonText = state.retryButtonText,
                onRetryClick = onRetryClick,
            )
        }
    }
}
