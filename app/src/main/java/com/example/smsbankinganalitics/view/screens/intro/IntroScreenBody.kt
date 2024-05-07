package com.example.smsbankinganalitics.view.screens.intro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.yml.charts.common.extensions.isNotNull
import com.example.smsbankinganalitics.view.widgets.IntroScreenPage
import com.example.smsbankinganalitics.view.widgets.IntroScreenSettingsPage
import com.example.smsbankinganalitics.view.widgets.IntroStartButton
import com.example.smsbankinganalitics.view.widgets.PageIndicator
import com.example.smsbankinganalitics.view_models.IntroVewModel
import com.example.smsbankinganalitics.view_models.SettingsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreenBody(
    innerPadding: PaddingValues,
    introVewModel: IntroVewModel,
    settingsViewModel: SettingsViewModel,
    navHostController: NavHostController
) {

    val pagerState = rememberPagerState(pageCount = {
        introVewModel.state.introItems.size
    })

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 24.dp,
            contentPadding = PaddingValues(8.dp)
        ) { pageIndex ->
            introVewModel.state.introItems[pageIndex].let { introPageItem ->
                if(introPageItem.resId.isNotNull()) {
                    IntroScreenPage(
                        introPageItem.resId!!,
                        introPageItem.header!!,
                        introPageItem.description!!
                    )
                } else {
                    IntroScreenSettingsPage(settingsViewModel)
                }
            }
        }
        PageIndicator(pagerState, this, 12)
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visible = pagerState.targetPage == 4,
            enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween()),
            exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween())
        ) {
            IntroStartButton(navHostController)
        }
    }
}