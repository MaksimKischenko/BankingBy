package com.example.smsbankinganalitics.view.screens.sms_banking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import com.example.smsbankinganalitics.view_models.SmsReceiverState
import com.example.smsbankinganalitics.view_models.UiEffectsEvent
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel
import com.example.smsbankinganalitics.view.widgets.SmsBodyItem
import com.example.smsbankinganalitics.view.widgets.SmsHeaderItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmsBankingScreenBody(
    innerPadding: PaddingValues,
    smsReceiverViewModelState: SmsReceiverState,
    pullRefreshState: PullRefreshState,
    uiEffectsViewModel: UiEffectsViewModel,
) {

    val lazyListState = rememberLazyListState()
    val snapshotFlow = snapshotFlow {
        lazyListState.firstVisibleItemIndex
    }
    val smsList = smsReceiverViewModelState.smsReceivedList ?: emptyList()

    LaunchedEffect(Unit) {
        uiEffectsViewModel.onEvent(
            UiEffectsEvent.ScrollingDownList(
                snapshotFlow,
            )
        )
    }
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .padding(innerPadding)
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter,
        ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            if(smsReceiverViewModelState.smsCommonInfo.isNotNull()) {
                SmsHeaderItem(smsReceiverViewModelState)
            }
            LazyColumn(
                state = lazyListState
            ) {
                itemsIndexed(smsList) { _, sms ->
                    SmsBodyItem(sms)
                }
            }
        }
        PullRefreshIndicator(
            refreshing = smsReceiverViewModelState.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.primary
        )

    }
}
