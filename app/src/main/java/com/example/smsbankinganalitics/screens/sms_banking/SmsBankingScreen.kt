package com.example.smsbankinganalitics.screens.sms_banking

import android.content.Context
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smsbankinganalitics.models.ErrorArgs
import com.example.smsbankinganalitics.models.SmsAddress
import com.example.smsbankinganalitics.models.SmsArgs
import com.example.smsbankinganalitics.view_models.SmsReceiverEvent
import com.example.smsbankinganalitics.view_models.SmsReceiverState
import com.example.smsbankinganalitics.view_models.SmsReceiverViewModel
import com.example.smsbankinganalitics.view_models.UiEffectsEvent
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel
import com.example.smsbankinganalitics.widgets.AppDrawer
import com.example.smsbankinganalitics.widgets.SmsAppBar
import com.example.smsbankinganalitics.widgets.SmsFilterDialog
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SmsBankingScreen(
    uiEffectsViewModel: UiEffectsViewModel,
    smsReceiverViewModel: SmsReceiverViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {


    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val dateState = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    val smsAddressState by remember {
        mutableStateOf(SmsAddress.BNB_BANK)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState =
        rememberPullRefreshState(refreshing = smsReceiverViewModel.state.isLoading, onRefresh = {
            onLoad(context, smsAddressState, smsReceiverViewModel)
        })
    val isVisibleAppBar = !(uiEffectsViewModel.stateApp.isUnVisibleBottomBar ?: true)

    LaunchedEffect(key1 = true) {
        onLoad(context, smsAddressState, smsReceiverViewModel)

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
            }) { padding ->
                SmsBankingScreenBody(
                    padding,
                    smsReceiverViewModel.state,
                    pullRefreshState,
                    uiEffectsViewModel,
                )
                SmsFilterDialog(
                    onSelect = {
                        onLoad(
                            context,
                            smsAddressState,
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
    smsAddressState: SmsAddress,
    smsReceiverViewModel: SmsReceiverViewModel,
    dateFrom: Long? = null,
) {
    val labelArray = when (smsAddressState) {
        SmsAddress.BNB_BANK -> SmsAddress.BNB_BANK.labelArray
        SmsAddress.BSB_BANK -> SmsAddress.BSB_BANK.labelArray
        SmsAddress.ASB_BANK -> SmsAddress.ASB_BANK.labelArray
    }
    smsReceiverViewModel.onEvent(
        SmsReceiverEvent.ByArgs(
            SmsArgs(
                labelArray, dateFrom, System.currentTimeMillis()
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

