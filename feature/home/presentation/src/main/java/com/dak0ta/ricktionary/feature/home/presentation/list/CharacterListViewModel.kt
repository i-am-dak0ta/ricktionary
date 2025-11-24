package com.dak0ta.ricktionary.feature.home.presentation.list

import androidx.lifecycle.viewModelScope
import com.dak0ta.ricktionary.core.mvvm.BaseViewModel
import com.dak0ta.ricktionary.feature.home.domain.usecase.GetCharactersPagerUseCase
import com.dak0ta.ricktionary.feature.home.presentation.list.ui.CharacterListStateMapper
import com.dak0ta.ricktionary.feature.home.presentation.list.ui.CharacterListUiState
import com.dak0ta.ricktionary.feature.home.presentation.navigation.CharacterDetailsDirection
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class CharacterListViewModel @Inject constructor(
    private val getCharactersPagerUseCase: GetCharactersPagerUseCase,
    uiStateMapper: CharacterListStateMapper,
) : BaseViewModel() {

    private val dataState = MutableStateFlow<CharacterListState>(CharacterListState.Loading)
    val uiState: StateFlow<CharacterListUiState> = dataState.map(uiStateMapper)
        .stateInViewModel(CharacterListUiState.Loading)
    private val _action = Channel<CharacterListAction>(Channel.BUFFERED)
    val action: Flow<CharacterListAction> = _action.receiveAsFlow()

    override fun onFirstInit() {
        loadData()
    }

    private fun loadData() {
        val pagerFlow = getCharactersPagerUseCase()
        dataState.value = CharacterListState.Content(characters = pagerFlow)
    }

    internal fun onCharacterClick(characterId: Int) {
        viewModelScope.launch {
            _action.send(
                CharacterListAction.NavigateTo(
                    CharacterDetailsDirection::class,
                    characterId,
                ),
            )
        }
    }

    internal fun onRetryClick() {
        dataState.update { CharacterListState.Loading }
        loadData()
    }
}
