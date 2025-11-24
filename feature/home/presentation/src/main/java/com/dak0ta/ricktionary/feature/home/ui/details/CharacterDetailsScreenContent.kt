package com.dak0ta.ricktionary.feature.home.ui.details

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.dak0ta.ricktionary.feature.home.presentation.details.ui.CharacterDetailsUiState
import com.dak0ta.ricktionary.feature.home.ui.details.widget.CharacterDetails
import com.dak0ta.ricktionary.feature.home.ui.test.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterDetailsScreenContent(
    state: CharacterDetailsUiState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is CharacterDetailsUiState.Loading -> {
            LoadingScreen()
        }

        is CharacterDetailsUiState.Content -> {
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
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                )
                            }
                        },
                    )
                },
            ) { innerPadding ->
                CharacterDetails(
                    character = state.character,
                    modifier = Modifier
                        .padding(innerPadding)
                        .testTag(TestTags.CHARACTER_DETAILS_SCREEN),
                )
            }
        }

        is CharacterDetailsUiState.Error -> {
            ErrorScreen(
                title = state.title,
                description = state.description,
                retryButtonText = state.retryButtonText,
                onRetryClick = onRetryClick,
            )
        }
    }
}
