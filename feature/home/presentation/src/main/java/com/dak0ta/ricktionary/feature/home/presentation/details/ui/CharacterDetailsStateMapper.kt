package com.dak0ta.ricktionary.feature.home.presentation.details.ui

import com.dak0ta.ricktionary.core.domain.CharacterDetail
import com.dak0ta.ricktionary.core.domain.CharacterGender
import com.dak0ta.ricktionary.core.domain.CharacterStatus
import com.dak0ta.ricktionary.feature.home.presentation.details.CharacterDetailsState
import javax.inject.Inject

internal class CharacterDetailsStateMapper @Inject constructor() : (CharacterDetailsState) -> CharacterDetailsUiState {

    override fun invoke(state: CharacterDetailsState): CharacterDetailsUiState {
        return when (state) {
            is CharacterDetailsState.Loading -> CharacterDetailsUiState.Loading
            is CharacterDetailsState.Content -> mapContentState(state)
            is CharacterDetailsState.Error -> mapErrorState()
        }
    }

    private fun mapContentState(state: CharacterDetailsState.Content): CharacterDetailsUiState.Content =
        CharacterDetailsUiState.Content(
            title = "Details about character",
            character = mapCharactersToUi(state.character),
        )

    private fun mapCharactersToUi(character: CharacterDetail): CharacterDetailUi {
        return CharacterDetailUi(
            id = character.id,
            name = character.name,
            status = "Status: ${mapStatusToString(character.status)}",
            species = "Species: ${character.species}",
            type = "Type: ${character.type}",
            gender = "Gender: ${mapGenderToString(character.gender)}",
            origin = "Origin: ${character.origin}",
            location = "Location: ${character.location}",
            image = character.image,
            episodes = character.episodes.map { episode ->
                EpisodeUi(
                    id = episode.id,
                    name = episode.name,
                    season = "Season: ${episode.season}",
                    episode = "Episode: ${episode.episode}",
                )
            },
        )
    }

    private fun mapStatusToString(status: CharacterStatus) = when (status) {
        CharacterStatus.ALIVE -> "Alive"
        CharacterStatus.DEAD -> "Dead"
        CharacterStatus.UNKNOWN -> "Unknown"
    }

    private fun mapGenderToString(gender: CharacterGender) = when (gender) {
        CharacterGender.MALE -> "Male"
        CharacterGender.FEMALE -> "Female"
        CharacterGender.GENDERLESS -> "Genderless"
        CharacterGender.UNKNOWN -> "Unknown"
    }

    private fun mapErrorState() = CharacterDetailsUiState.Error(
        title = "Что-то пошло не так",
        description = "Не удалось загрузить данные. Попробуйте снова позже.",
        retryButtonText = "Попробовать снова",
    )
}
