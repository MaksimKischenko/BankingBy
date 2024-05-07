package com.production.smsbankinganalitics.view.screens.sms_banking

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.production.smsbankinganalitics.model.ErrorArgs
import com.production.smsbankinganalitics.model.SmsAddress
import com.production.smsbankinganalitics.model.SmsArgs
import com.production.smsbankinganalitics.view_models.SmsReceiverEvent
import com.production.smsbankinganalitics.view_models.SmsReceiverState
import com.production.smsbankinganalitics.view_models.SmsReceiverViewModel
import com.production.smsbankinganalitics.view_models.UiEffectsEvent
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel
import com.production.smsbankinganalitics.view.widgets.AppDrawer
import com.production.smsbankinganalitics.view.widgets.SmsAppBar
import com.production.smsbankinganalitics.view.widgets.SmsFilterDialog
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SmsBankingScreen(
    smsAddress: SmsAddress,
    uiEffectsViewModel: UiEffectsViewModel,
    smsReceiverViewModel: SmsReceiverViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val dateState = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = smsReceiverViewModel.state.isLoading,
            onRefresh = {
            onLoad(context, smsAddress, smsReceiverViewModel)
        })
    val isVisibleAppBar = !(uiEffectsViewModel.state.isUnVisibleBottomBar ?: true)

    LaunchedEffect(key1 = true) {
        onLoad(context, smsAddress, smsReceiverViewModel)

    }
    SideEffect {
        onError(
            uiEffectsViewModel, smsReceiverViewModel.state, snackbarHostState
        )
    }
    AppDrawer(
        smsReceiverViewModel.state,
        uiEffectsViewModel,
        scope,
        drawerState,
        content = {
            Scaffold(
                topBar = {
                if (smsReceiverViewModel.state.smsReceivedList?.isNotEmpty() != false)
                    AnimatedVisibility(
                        visible = isVisibleAppBar,
                        enter = slideInHorizontally(
                            initialOffsetX = { it }, animationSpec = tween()
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it }, animationSpec = tween()
                        )
                    ) {
                        SmsAppBar(
                            smsReceiverViewModel.state,
                            onDrawerClick = {
                                uiEffectsViewModel.onEvent(UiEffectsEvent.HideBottomBar(true))
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            onFilterClick = {
                                openDialog.value = true
                            }
                        )
                    }
            }, snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                ) {
                    Snackbar(
                        containerColor = MaterialTheme.colorScheme.onPrimary, snackbarData = it,
                    )
                }
            }) {
                SmsBankingScreenBody(
                    it,
                    smsReceiverViewModel.state,
                    pullRefreshState,
                    uiEffectsViewModel,
                )
                SmsFilterDialog(
                    onSelect = {
                        onLoad(
                            context,
                            smsAddress,
                            smsReceiverViewModel,
                            dateState.selectedDateMillis
                        )
                    }, dateState, openDialog = openDialog
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun onLoad(
    context: Context,
    smsAddress: SmsAddress,
    smsReceiverViewModel: SmsReceiverViewModel,
    dateFrom: Long? = null,
) {
    smsReceiverViewModel.onEvent(
        SmsReceiverEvent.ByArgs(
            SmsArgs(
                smsAddress, dateFrom, System.currentTimeMillis()
            ), context
        )
    )
}

fun onError(
    uiEffectsViewModel: UiEffectsViewModel,
    smsReceiverViewModelState: SmsReceiverState,
    snackbarHostState: SnackbarHostState
) {
    if (smsReceiverViewModelState.errorMessage != null) {
        val errorArgs = ErrorArgs(smsReceiverViewModelState.errorMessage!!, true)
        uiEffectsViewModel.onEvent(UiEffectsEvent.ShowingError(errorArgs, snackbarHostState))
    }
}

