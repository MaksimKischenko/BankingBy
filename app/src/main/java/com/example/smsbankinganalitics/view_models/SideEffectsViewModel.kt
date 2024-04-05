package com.example.smsbankinganalitics.view_models


import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smsbankinganalitics.models.ErrorArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SideEffectsViewModel @Inject constructor() : ViewModel() {

    var stateApp by mutableStateOf(SideEffectsState())

    fun onEvent(event: SideEffectsEvent): SideEffectsViewModel {

        stateApp = when (event) {
            is SideEffectsEvent.ShowingErrorEvent -> {
                stateApp.copy(
                    isUnVisibleBottomBar = event.errorArgs.isShowingError,
                    errorMessage = event.errorArgs.errorMessage
                )
            }

            is SideEffectsEvent.ScrollingDownListEvent -> {
                stateApp.copy(
                    isUnVisibleBottomBar = event.isScrollingDown,
                )
            }
        }
        return this
    }

    fun showErrorSnackBar(
        scope: CoroutineScope, snackbarHostState: SnackbarHostState
    ) {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                withDismissAction = true,
                message = stateApp.errorMessage ?: "",
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* Handle snackbar action performed */
                }

                SnackbarResult.Dismissed -> {
                    stateApp = stateApp.copy(
                        isUnVisibleBottomBar = false,
                        errorMessage = null
                    )
                }
            }
        }
    }
}

sealed class SideEffectsEvent {
    data class ShowingErrorEvent(val errorArgs: ErrorArgs) : SideEffectsEvent()
    data class ScrollingDownListEvent(val isScrollingDown: Boolean) : SideEffectsEvent()
}

data class SideEffectsState(
    val errorMessage: String? = "",
    val isUnVisibleBottomBar: Boolean? = false,
)