package com.dak0ta.ricktionary.feature.home.presentation.details

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dak0ta.ricktionary.core.coroutine.runSuspendCatching
import com.dak0ta.ricktionary.core.mvvm.BaseViewModel
import com.dak0ta.ricktionary.feature.home.domain.usecase.GetCharacterByIdUseCase
import com.dak0ta.ricktionary.feature.home.presentation.details.ui.CharacterDetailsStateMapper
import com.dak0ta.ricktionary.feature.home.presentation.details.ui.CharacterDetailsUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    uiStateMapper: CharacterDetailsStateMapper,
) : BaseViewModel() {

    private var characterId: Int? = null
    private val dataState = MutableStateFlow<CharacterDetailsState>(CharacterDetailsState.Loading)
    val uiState: StateFlow<CharacterDetailsUiState> = dataState.map(uiStateMapper)
        .stateInViewModel(CharacterDetailsUiState.Loading)
    private val _action = Channel<CharacterDetailsAction>(Channel.BUFFERED)
    val action: Flow<CharacterDetailsAction> = _action.receiveAsFlow()

    internal fun initWithId(id: Int) {
        if (characterId != null) return
        characterId = id
        loadData()
    }

    private fun loadData() {
        val id = characterId ?: return
        viewModelScope.launch {
            runSuspendCatching {
                getCharacterByIdUseCase(id)
            }
                .onSuccess { user ->
                    dataState.value = CharacterDetailsState.Content(user)
                }
                .onFailure {
                    Log.e(TAG, "getCharacterByIdUseCase has failed", it)
                    dataState.value = CharacterDetailsState.Error
                }
        }
    }

    internal fun onBackClick() {
        viewModelScope.launch {
            _action.send(CharacterDetailsAction.NavigateBack)
        }
    }

    internal fun onRetryClick() {
        dataState.update { CharacterDetailsState.Loading }
        loadData()
    }

    private companion object {

        const val TAG = "CharacterDetailsViewModel"
    }
}
