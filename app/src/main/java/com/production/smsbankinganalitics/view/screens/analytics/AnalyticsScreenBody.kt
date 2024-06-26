package com.production.smsbankinganalitics.view.screens.analytics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.AnalyticsState
import com.production.smsbankinganalitics.view_models.AnalyticsViewModel
import com.production.smsbankinganalitics.view.widgets.DonutPiePage
import com.production.smsbankinganalitics.view.widgets.EmptyScreenInfo
import com.production.smsbankinganalitics.view.widgets.PageIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnalyticsScreenBody(
    innerPadding: PaddingValues,
    analyticsViewModel: AnalyticsViewModel,
    appBarTitleName: MutableState<String>,
) {
    val pagerState = rememberPagerState(pageCount = {
        analyticsViewModel.state.paymentPieChartDataMap.size
    })

    LaunchedEffect(pagerState.targetPage) {
        withContext(Dispatchers.Default) {
            if (analyticsViewModel.state.paymentPieChartDataMap.keys.isNotEmpty()) {
                val result = analyticsViewModel.state.paymentPieChartDataMap.keys.toList()[pagerState.targetPage]
                appBarTitleName.value = result
            }
        }
    }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .padding(innerPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LoadingBody(analyticsViewModel)
        if(analyticsViewModel.state.paymentPieChartDataMap.values.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 24.dp,
                contentPadding = PaddingValues(8.dp)
            ) { pageIndex ->
                analyticsViewModel.state.paymentPieChartDataMap.values.toList()[pageIndex]?.let { data ->
                    analyticsViewModel.state.donutChartConfig?.let { config ->
                        DonutPiePage(
                            pieChartData = data,
                            dateFrom = dateFromAnalyzer(analyticsViewModel.state, pageIndex),
                            donutChartConfig = config.copy(
                                labelColor = MaterialTheme.colorScheme.secondary,
                                backgroundColor = MaterialTheme.colorScheme.tertiary
                            ),
                            graphicsLayer = {
                                val pageOffset =
                                    ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction).absoluteValue
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                        )
                    }
                }
            }
        } else {
            EmptyScreenInfo(R.drawable.analytics_search)
        }
        PageIndicator(pagerState, this)
    }
}

fun dateFromAnalyzer(state: AnalyticsState, pageIndex: Int) : String {
    return if(pageIndex == 1) {
        return LocalDateTime.now().year.toString()
    } else {
        state.dateFrom?:""
    }
}


@Composable
fun LoadingBody(
    analyticsViewModel: AnalyticsViewModel
) {
    if (!analyticsViewModel.state.isLoading) return

    CircularProgressIndicator(
        modifier = Modifier
            .size(120.dp),
        color = MaterialTheme.colorScheme.tertiary,
        strokeWidth = 16.dp,
        trackColor = MaterialTheme.colorScheme.primary,
    )
}