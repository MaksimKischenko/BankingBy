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
import com.example.smsbankinganalitics.model.ErrorArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UiEffectsViewModel @Inject constructor() : ViewModel() {

    var stateApp by mutableStateOf(SideEffectsState())
    private var lastState: SideEffectsState? = null

    fun onEvent(event: UiEffectsEvent) {

        when (event) {
            is UiEffectsEvent.ShowingError -> {
                stateApp = stateApp.copy(
                    isUnVisibleBottomBar = event.errorArgs.isShowingError,
                    errorMessage = event.errorArgs.message
                )
                if(lastState != stateApp) {
                    showErrorSnackBar(event.snackbarHostState)
                }
                lastState = stateApp
            }

            is UiEffectsEvent.ScrollingDownList -> {
                val isScrollingDownState = MutableStateFlow(false)
                publishScrollingDownState(isScrollingDownState, event)
                subscribeScrollingDownState(isScrollingDownState, event)
            }

            is UiEffectsEvent.HideBottomBar -> {
                stateApp = if(event.hideBottomBar) {
                    stateApp.copy(
                        isUnVisibleBottomBar = true
                    )
                } else {
                    stateApp.copy(
                        isUnVisibleBottomBar = false
                    )
                }
            }
        }
    }


    //Для оптимизации кода и вызова изменения состояния только при изменении переменной  isScrollingDown ,
    // вы можете воспользоваться паттерном "Publish-Subscribe" (Издатель-Подписчик) или использовать  StateFlow
    // для отслеживания изменений переменной  isScrollingDown
    private fun publishScrollingDownState(
        isScrollingDownState: MutableStateFlow<Boolean>,
        event: UiEffectsEvent.ScrollingDownList
    ) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            event.snapshotFlow.collect { firstVisibleItemIndex ->
                // Проверяем, изменилось ли положение первого видимого элемента
                val newIsScrollingDown =
                    firstVisibleItemIndex > 2 //lazyListState.layoutInfo.totalItemsCount / 100
                if (isScrollingDownState.value != newIsScrollingDown) {
                    isScrollingDownState.value = newIsScrollingDown
                }
            }
        }
    }

    private fun subscribeScrollingDownState(
        isScrollingDownState: MutableStateFlow<Boolean>,
        event: UiEffectsEvent.ScrollingDownList
    ) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            isScrollingDownState.collect { isScrollingDown ->
                stateApp = stateApp.copy(
                    isUnVisibleBottomBar = isScrollingDown,
                )
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
                    Log.d("MyLog", "DISMISS")
                    stateApp = stateApp.copy(
                        isUnVisibleBottomBar = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun showInfoSnackBar(
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

sealed class UiEffectsEvent {
    data class ShowingError(
        val errorArgs: ErrorArgs,
        val snackbarHostState: SnackbarHostState
    ) : UiEffectsEvent()

    data class ScrollingDownList(
        val snapshotFlow: Flow<Int>,
    ) : UiEffectsEvent()

    data class HideBottomBar(
        val hideBottomBar: Boolean,
    ) : UiEffectsEvent()
}

data class SideEffectsState(
    val errorMessage: String? = null,
    val infoMessage: String? = null,
    val isUnVisibleBottomBar: Boolean? = false,
)