package com.example.smsbankinganalitics.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smsbankinganalitics.models.NavBarItem
import com.example.smsbankinganalitics.screens.analytics.AnalyticsScreen
import com.example.smsbankinganalitics.screens.settings.SettingsScreen
import com.example.smsbankinganalitics.screens.sms_banking.SmsBankingScreen
import com.example.smsbankinganalitics.view_models.ThemeViewModel
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavGraphBody(
    themeViewModel: ThemeViewModel,
    uiEffectsViewModel: UiEffectsViewModel,
    innerPadding: PaddingValues,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = NavBarItem.SmsBankingItem.route,
        builder = {
            composable(NavBarItem.SmsBankingItem.route) {
                SmsBankingScreen(uiEffectsViewModel)
            }
            composable(NavBarItem.OperationsItem.route) {
                AnalyticsScreen(navHostController, uiEffectsViewModel)
            }
            composable(NavBarItem.SettingsItem.route) {
                SettingsScreen(navHostController, themeViewModel)
            }
        }
    )
}