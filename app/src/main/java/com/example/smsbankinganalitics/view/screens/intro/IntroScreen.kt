package com.example.smsbankinganalitics.view.screens.intro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.model.IntroPageItem
import com.example.smsbankinganalitics.view.widgets.IntroScreenPage
import com.example.smsbankinganalitics.view.widgets.PageIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreen(
    navHostController: NavHostController,
) {
    val introItems by lazy {
        listOf(
            IntroPageItem(R.drawable.sms_search, R.string.introHeader_1, R.string.introDesc_1),
            IntroPageItem(R.drawable.transaction, R.string.introHeader_2, R.string.introDesc_2),
            IntroPageItem(R.drawable.analitics, R.string.introHeader_3, R.string.introDesc_3)
        )
    }
    val pagerState = rememberPagerState(pageCount = {introItems.size})

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
            introItems[pageIndex].let { introPageItem->
                IntroScreenPage(
                    navHostController,
                    pagerState,
                    introPageItem.resId,
                    introPageItem.headerId,
                    introPageItem.descriptionId
                )
            }
        }
    }
}