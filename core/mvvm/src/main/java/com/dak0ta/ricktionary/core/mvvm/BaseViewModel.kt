package com.dak0ta.ricktionary.core.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel : ViewModel() {

    private var isInitialized = false

    init {
        Log.d(TAG, "init ${this::class.simpleName}")
    }

    fun initialize() {
        if (!isInitialized) {
            onFirstInit()
            isInitialized = true
        }
    }

    open fun onFirstInit() {}

    protected fun <T> Flow<T>.stateInViewModel(initialState: T): StateFlow<T> = stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = initialState,
    )

    protected fun <T> Flow<T>.stateInViewModel(scope: CoroutineScope, initialState: T): StateFlow<T> = stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = initialState,
    )

    override fun onCleared() {
        Log.d(TAG, "onCleared ${this::class.simpleName}")
        super.onCleared()
    }

    private companion object {

        const val TAG = "Ricktionary:BaseViewModel"
    }
}
