package com.example.smsbankinganalitics.view.screens.intro

import android.util.Log
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
import com.example.smsbankinganalitics.view.widgets.IntroScreenPage
import com.example.smsbankinganalitics.view_models.IntroVewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreenBody(
    innerPadding: PaddingValues,
    introVewModel: IntroVewModel,
    navHostController: NavHostController
) {

    val pagerState = rememberPagerState(pageCount = {
        introVewModel.state.introItems.size
    })

    Log.d("MyLog", "SIZE:${introVewModel.state.introItems.size}")

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    )  {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 24.dp,
            userScrollEnabled = false,
            contentPadding = PaddingValues(8.dp)
        ) { pageIndex ->
            introVewModel.state.introItems[pageIndex].let { introPageItem->
                IntroScreenPage(
                    navHostController,
                    pagerState,
                    introPageItem.resId,
                    introPageItem.header,
                    introPageItem.description
                )
            }
        }
    }
}