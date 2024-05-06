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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.yml.charts.common.extensions.isNotNull
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.view.widgets.IntroScreenPage
import com.example.smsbankinganalitics.view.widgets.IntroScreenSettingsPage
import com.example.smsbankinganalitics.view.widgets.PageIndicator
import com.example.smsbankinganalitics.view_models.IntroVewModel
import com.example.smsbankinganalitics.view_models.SettingsViewModel
import com.example.smsbankinganalitics.view_models.utils.Localization
import com.example.smsbankinganalitics.view_models.utils.Navigation

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
//            userScrollEnabled = false,
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
            Button(
                onClick = {
                    Navigation.goToSmsBanking(navHostController, isFirstLoad = true)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),

                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.elevatedButtonColors().copy(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(0.9f)
                )
            ) {
                Text(
                    Localization.withComposable(R.string.start_button),
                    modifier = Modifier.padding(vertical = 4.dp),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center
                    ),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                )
            }
        }
    }
}