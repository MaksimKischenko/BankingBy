package com.example.smsbankinganalitics.screens.sms_banking

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.example.smsbankinganalitics.view_models.SMSReceiverEvent
import com.example.smsbankinganalitics.view_models.SideEffectsEvent
import com.example.smsbankinganalitics.view_models.SideEffectsViewModel
import com.example.smsbankinganalitics.view_models.SmsReceiverViewModel
import kotlinx.coroutines.CoroutineScope


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmsBankingScreen(
    sideEffectsViewModel: SideEffectsViewModel,
    smsReceiverViewModel: SmsReceiverViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val smsAddressState by remember {
        mutableStateOf(SmsAddress.BNB)
    }
    val smsReceiverViewModelState = smsReceiverViewModel.stateApp
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = smsReceiverViewModelState.isLoading,
            onRefresh = {
                onSmsReceiverViewModelEventTrigger(context, smsAddressState, smsReceiverViewModel)
            })

    LaunchedEffect(key1 = true) {
        onSmsReceiverViewModelEventTrigger(context, smsAddressState, smsReceiverViewModel)
    }

    SideEffect {
        if (smsReceiverViewModelState.errorMessage != null) {
            onSideEffectsViewModelEventTrigger(
                ErrorArgs(smsReceiverViewModelState.errorMessage!!, true),
                sideEffectsViewModel,
                scope,
                snackbarHostState
            )
        }
    }

    Scaffold(topBar = {

    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState
        ) {
            Snackbar(
                containerColor = MaterialTheme.colorScheme.onPrimary, snackbarData = it,
            )
        }
    }) { padding ->
        SmsBankingScreenBody(padding, smsReceiverViewModelState, pullRefreshState, sideEffectsViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun onSmsReceiverViewModelEventTrigger(
    context: Context,
    smsAddressState: SmsAddress,
    smsReceiverViewModel: SmsReceiverViewModel
) {
    when (smsAddressState) {
        SmsAddress.BNB -> smsReceiverViewModel.onEvent(
            SMSReceiverEvent.SMSReceiverByArgs(
                SmsArgs(
                    SmsAddress.BNB.labelArray
                ), context
            )
        )

        SmsAddress.BSB -> smsReceiverViewModel.onEvent(
            SMSReceiverEvent.SMSReceiverByArgs(
                SmsArgs(
                    SmsAddress.BSB.labelArray
                ), context
            )
        )

        SmsAddress.ASB -> smsReceiverViewModel.onEvent(
            SMSReceiverEvent.SMSReceiverByArgs(
                SmsArgs(
                    SmsAddress.ASB.labelArray
                ), context
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun onSideEffectsViewModelEventTrigger(
    errorArgs: ErrorArgs,
    sideEffectsViewModel: SideEffectsViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    sideEffectsViewModel
        .onEvent(SideEffectsEvent.ShowingErrorEvent(errorArgs))
        .showErrorSnackBar(scope, snackbarHostState)
}


