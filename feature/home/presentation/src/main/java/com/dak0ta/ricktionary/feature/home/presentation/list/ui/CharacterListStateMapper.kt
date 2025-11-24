package com.dak0ta.ricktionary.feature.home.presentation.list.ui

import androidx.paging.PagingData
import androidx.paging.map
import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.feature.home.presentation.list.CharacterListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CharacterListStateMapper @Inject constructor() : (CharacterListState) -> CharacterListUiState {

    override fun invoke(state: CharacterListState): CharacterListUiState {
        return when (state) {
            is CharacterListState.Loading -> CharacterListUiState.Loading
            is CharacterListState.Content -> mapContentState(state)
            is CharacterListState.Error -> mapErrorState()
        }
    }

    private fun mapContentState(state: CharacterListState.Content): CharacterListUiState.Content =
        CharacterListUiState.Content(
            title = "Characters",
            characters = mapCharactersToUi(state.characters),
        )

    private fun mapCharactersToUi(
        characters: Flow<PagingData<CharacterSummary>>,
    ): Flow<PagingData<CharacterSummaryUi>> {
        return characters.map { pagingData ->
            pagingData.map { character ->
                CharacterSummaryUi(
                    id = character.id,
                    name = character.name,
                    status = "Status: ${mapStatusToString(character.status)}",
                    species = "Species: ${character.species}",
                    image = character.image,
                )
            }
        }
    }

    private fun mapStatusToString(status: CharacterStatus) = when (status) {
        CharacterStatus.ALIVE -> "Alive"
        CharacterStatus.DEAD -> "Dead"
        CharacterStatus.UNKNOWN -> "Unknown"
    }

    private fun mapErrorState() = CharacterListUiState.Error(
        title = "Что-то пошло не так",
        description = "Не удалось загрузить данные. Попробуйте снова позже.",
        retryButtonText = "Попробовать снова",
    )
}
