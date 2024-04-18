package com.example.smsbankinganalitics.view.screens.analytics


import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.view_models.AnalyticsEvent
import com.example.smsbankinganalitics.view_models.AnalyticsViewModel
import com.example.smsbankinganalitics.view_models.UiEffectsEvent
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel
import com.example.smsbankinganalitics.view.widgets.AnalyticsAppBar


@Composable
fun AnalyticsScreen(
    navController: NavHostController,
    uiEffectsViewModel: UiEffectsViewModel,
    analyticsViewModel: AnalyticsViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    LaunchedEffect(Unit) {
        uiEffectsViewModel.onEvent(UiEffectsEvent.HideBottomBar(true))
        analyticsViewModel.onEvent(AnalyticsEvent.ByActionCategories(context))
    }

    val appBarTitleName = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            AnalyticsAppBar(
                uiEffectsViewModel,
                appBarTitleName,
                navController
            )
        }) { padding ->
        AnalyticsScreenBody(
            padding,
            analyticsViewModel,
            appBarTitleName
        )
    }
}
