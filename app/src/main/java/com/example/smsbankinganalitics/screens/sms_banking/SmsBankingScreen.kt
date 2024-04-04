package com.example.smsbankinganalitics.screens.sms_banking

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smsbankinganalitics.models.SmsAddress
import com.example.smsbankinganalitics.models.SmsArgs
import com.example.smsbankinganalitics.view_models.SMSReceiverEvent

import com.example.smsbankinganalitics.view_models.SMSReceiverViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmsBankingScreen(
    viewModel: SMSReceiverViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val smsAddressState by remember {
        mutableStateOf(SmsAddress.BNB)
    }
    val viewModelState = viewModel.stateApp
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = viewModelState.isLoading,
            onRefresh = {
                onEventTrigger(context, smsAddressState, viewModel)
            })
    LaunchedEffect(key1 = true) {
        onEventTrigger(context, smsAddressState, viewModel)
    }

    Scaffold(topBar = {

    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState
        ) {
            Snackbar(
                containerColor = MaterialTheme.colorScheme.primary, snackbarData = it
            )
        }
    }) { padding ->
        SmsBankingScreenBody(padding, viewModelState, pullRefreshState)
        if (viewModelState.errorMessage != null) viewModel.showErrorSnackBar(
            scope, snackbarHostState
        )
    }
}

fun onEventTrigger(
    context: Context,
    smsAddressState: SmsAddress,
    viewModel: SMSReceiverViewModel
) {
    when (smsAddressState) {
        SmsAddress.BNB -> SmsAddress.BNB.labelArray.forEach {
            viewModel.onEvent(SMSReceiverEvent.SMSReceiverByArgs(SmsArgs(it), context))
        }

        SmsAddress.BSB -> SmsAddress.BSB.labelArray.forEach {
            viewModel.onEvent(SMSReceiverEvent.SMSReceiverByArgs(SmsArgs(it), context))
        }

        SmsAddress.ASB -> SmsAddress.ASB.labelArray.forEach {
            viewModel.onEvent(SMSReceiverEvent.SMSReceiverByArgs(SmsArgs(it), context))
        }
    }

}

