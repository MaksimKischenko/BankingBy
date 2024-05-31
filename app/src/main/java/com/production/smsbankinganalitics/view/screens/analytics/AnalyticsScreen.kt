package com.production.smsbankinganalitics.view.screens.analytics


import android.content.Context
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.production.smsbankinganalitics.model.SmsAddress
import com.production.smsbankinganalitics.view_models.AnalyticsEvent
import com.production.smsbankinganalitics.view_models.AnalyticsViewModel
import com.production.smsbankinganalitics.view_models.UiEffectsEvent
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel
import com.production.smsbankinganalitics.view.widgets.AnalyticsAppBar


@Composable
fun AnalyticsScreen(
    smsAddress: SmsAddress,
    navHostController: NavHostController,
    uiEffectsViewModel: UiEffectsViewModel,
    analyticsViewModel: AnalyticsViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val appBarTitleName = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
//        uiEffectsViewModel.onEvent(UiEffectsEvent.HideBottomBar(true))
        analyticsViewModel.onEvent(AnalyticsEvent.ByActionCategories(context, smsAddress))
    }


    Scaffold(
        topBar = {
            AnalyticsAppBar(
                uiEffectsViewModel,
                appBarTitleName,
                navHostController
            )
        }) { padding ->
        AnalyticsScreenBody(
            padding,
            analyticsViewModel,
            appBarTitleName
        )
    }
}

