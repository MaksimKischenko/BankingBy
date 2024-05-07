package com.production.smsbankinganalitics.view_models


import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.production.smsbankinganalitics.model.ErrorArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UiEffectsViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(SideEffectsState())
    private var lastState: SideEffectsState? = null

    fun onEvent(event: UiEffectsEvent) {

        when (event) {
            is UiEffectsEvent.ShowingError -> {
                state = state.copy(
                    isUnVisibleBottomBar = event.errorArgs.isShowingError,
                    errorMessage = event.errorArgs.message
                )
                if(lastState != state) {
                    showErrorSnackBar(event.snackbarHostState)
                }
                lastState = state
            }

            is UiEffectsEvent.ScrollingDownList -> {
                val isScrollingDownState = MutableStateFlow(false)
                publishScrollingDownState(isScrollingDownState, event)
                subscribeScrollingDownState(isScrollingDownState)
            }

            is UiEffectsEvent.HideBottomBar -> {
                state = if(event.hideBottomBar) {
                    state.copy(
                        isUnVisibleBottomBar = true
                    )
                } else {
                    state.copy(
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
    ) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            isScrollingDownState.collect { isScrollingDown ->
                state = state.copy(
                    isUnVisibleBottomBar = isScrollingDown,
                )
            }
        }
    }


    private fun showErrorSnackBar(snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            val result = snackbarHostState.showSnackbar(
                withDismissAction = true,
                message = state.errorMessage ?: "",
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* Handle snackbar action performed */
                }

                SnackbarResult.Dismissed -> {
                    Log.d("MyLog", "DISMISS")
                    state = state.copy(
                        isUnVisibleBottomBar = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    @Composable
    fun shimmerBrush(
        color: Color,
    ): Brush {
        return if (state.showShimmer) {
            val shimmerColors = listOf(
                color.copy(alpha = 0.4f),
                color.copy(alpha = 0.2f),
                color.copy(alpha = 0.4f),
            )
            val transition = rememberInfiniteTransition(label = "")
            val translateAnimation = transition.animateFloat(
                initialValue = 0f,
                targetValue = 1000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(800), repeatMode = RepeatMode.Reverse
                ), label = ""
            )
            Brush.linearGradient(
                colors = shimmerColors,
                start = Offset.Zero,
                end = Offset(x = translateAnimation.value, y = translateAnimation.value)
            )
        } else {
            Brush.linearGradient(
                colors = listOf(Color.Transparent, Color.Transparent),
                start = Offset.Zero,
                end = Offset.Zero
            )
        }
    }

    private fun showInfoSnackBar(
        snackbarHostState: SnackbarHostState,
    ) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(
                message = state.infoMessage ?: "",
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
    val isUnVisibleBottomBar: Boolean? = true,
    val showShimmer: Boolean = true
)