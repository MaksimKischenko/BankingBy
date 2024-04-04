package com.example.smsbankinganalitics.screens.sms_banking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.view_models.SMSReceiverState
import com.example.smsbankinganalitics.widgets.SmsBodyItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmsBankingScreenBody(
    innerPadding: PaddingValues,
    viewModelState: SMSReceiverState,
    pullRefreshState: PullRefreshState,
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.padding(
                vertical = 24.dp
            )
        ) {
            items(count = viewModelState.smsReceivedList?.count() ?: 0) { index ->
                SmsBodyItem(
                    viewModelState.smsReceivedList?.get(index),
                )
            }
        }
        PullRefreshIndicator(
            refreshing = viewModelState.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}