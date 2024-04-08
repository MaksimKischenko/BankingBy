package com.example.smsbankinganalitics.view_models


import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smsbankinganalitics.models.ErrorArgs
import com.example.smsbankinganalitics.models.InfoArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SideEffectsViewModel @Inject constructor() : ViewModel() {

    var stateApp by mutableStateOf(SideEffectsState())

    fun onEvent(event: SideEffectsEvent){

        when (event) {
            is SideEffectsEvent.ShowingErrorEvent -> {
                stateApp = stateApp.copy(
                    isUnVisibleBottomBar = event.errorArgs.isShowingError,
                    errorMessage = event.errorArgs.message
                )
                showErrorSnackBar(event.snackbarHostState)
            }

            is SideEffectsEvent.ScrollingDownListEvent -> {
                stateApp = stateApp.copy(
                    isUnVisibleBottomBar = event.isScrollingDown,
                    infoMessage = event.infoArgs.message
                )
                Log.d("MyLog", "SPAM")
            }
        }
    }


    private fun showErrorSnackBar(snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
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

    fun showInfoSnackBar(
        snackbarHostState: SnackbarHostState,
    ) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(
                message = stateApp.infoMessage ?: "",
                duration = SnackbarDuration.Short
            )
        }
    }
}

sealed class SideEffectsEvent {
    data class ShowingErrorEvent(
        val errorArgs: ErrorArgs,
        val snackbarHostState: SnackbarHostState
    ) : SideEffectsEvent()

    data class ScrollingDownListEvent(
        val infoArgs: InfoArgs,
        val isScrollingDown: Boolean,
        val snackbarHostState: SnackbarHostState
    ) : SideEffectsEvent()
}

data class SideEffectsState(
    val errorMessage: String? = "",
    val infoMessage: String? = "",
    val isUnVisibleBottomBar: Boolean? = false,
)