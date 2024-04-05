package com.example.smsbankinganalitics.screens.sms_banking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.view_models.SMSReceiverState
import com.example.smsbankinganalitics.view_models.SideEffectsEvent
import com.example.smsbankinganalitics.view_models.SideEffectsViewModel
import com.example.smsbankinganalitics.widgets.SmsBodyItem

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmsBankingScreenBody(
    innerPadding: PaddingValues,
    viewModelState: SMSReceiverState,
    pullRefreshState: PullRefreshState,
    sideEffectsViewModel: SideEffectsViewModel,
) {

    val smsList = viewModelState.smsReceivedList ?: emptyList()

    // Определяем состояние прокрутки
    val lazyListState = rememberLazyListState()

    // Переменная для хранения направления прокрутки
    var isScrollingDown by remember { mutableStateOf(false) }

    // Слушатель прокрутки для LazyColumn
    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }.collect { firstVisibleItemIndex ->
                // Проверяем, изменилось ли положение первого видимого элемента
                val newIsScrollingDown =
                    firstVisibleItemIndex > 2 //lazyListState.layoutInfo.totalItemsCount / 100
                if (isScrollingDown != newIsScrollingDown) {
                    isScrollingDown = newIsScrollingDown
                }
                sideEffectsViewModel.onEvent(SideEffectsEvent.ScrollingDownListEvent(isScrollingDown))
                Log.d("MyLog", "isScrollingDown $isScrollingDown")
            }
    }


    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 24.dp),
            state = lazyListState
        ) {
            itemsIndexed(smsList) { _, sms ->
                SmsBodyItem(sms)
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
