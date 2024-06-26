package com.production.smsbankinganalitics.view.screens.sms_banking


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import co.yml.charts.common.extensions.isNotNull
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view.widgets.EmptyScreenInfo
import com.production.smsbankinganalitics.view.widgets.EmptyShimmerBrushCardList
import com.production.smsbankinganalitics.view_models.SmsReceiverState
import com.production.smsbankinganalitics.view_models.UiEffectsEvent
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel
import com.production.smsbankinganalitics.view.widgets.SmsBodyItem
import com.production.smsbankinganalitics.view.widgets.SmsHeaderItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmsBankingScreenBody(
    innerPadding: PaddingValues,
    smsReceiverViewModelState: SmsReceiverState,
    pullRefreshState: PullRefreshState,
    uiEffectsViewModel: UiEffectsViewModel,
) {

    val lazyListState = rememberLazyListState()

    val snapshotFlow = remember {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }
    }

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
            modifier = Modifier.fillMaxSize()

        ) {
            if(smsReceiverViewModelState.smsCommonInfo.isNotNull()) {
                SmsHeaderItem(
                    smsReceiverViewModelState,
                    uiEffectsViewModel
                )
            }
            if(smsReceiverViewModelState.smsReceivedList?.isNotEmpty() != false) {
                LazyColumn(
                    state = lazyListState
                ) {
                    items(smsReceiverViewModelState.smsReceivedList?.size?:0) { index ->
                        key(index) {
                            SmsBodyItem(smsReceiverViewModelState.smsReceivedList?.get(index))
                        }
                    }
                }
            } else {
                if(smsReceiverViewModelState.isLoading) {
                    EmptyShimmerBrushCardList(uiEffectsViewModel)
                } else {
                    EmptyScreenInfo(R.drawable.analytics_search)
                }
            }
        }
        PullRefreshIndicator(
            refreshing = smsReceiverViewModelState.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}



