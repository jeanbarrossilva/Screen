package com.jeanbarrossilva.screen.interop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/** [ViewModel] that holds the [UiState] of a [Screen]. **/
abstract class ScreenViewModel<S: Screen, ST: UiState<S>>(protected val screen: S): ViewModel() {
    /**
     * [MutableStateFlow] that receives a new [UiState] at each update.
     *
     * @see updateUiState
     **/
    private val uiStateFlow by lazy { MutableStateFlow(initialUiState) }

    /** Initial value for [uiStateFlow]. **/
    protected abstract val initialUiState: ST

    /** Shorthand for getting the value of [uiStateFlow]. **/
    val uiState
        get() = uiStateFlow.value

    /**
     * Emits the current [UiState] with the given [update] applied to it.
     *
     * @param update Update to be performed to [uiState].
     **/
    protected fun updateUiState(update: suspend (state: ST) -> ST) {
        viewModelScope.launch {
            uiStateFlow.emit(update(uiStateFlow.value))
        }
    }

    /** Gets [uiStateFlow] as a [StateFlow]. **/
    fun getUiStateFlow(): StateFlow<ST> {
        return uiStateFlow
    }
}