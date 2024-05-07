package com.production.smsbankinganalitics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.production.smsbankinganalitics.view_models.utils.BottomNavBarItem
import com.production.smsbankinganalitics.view_models.utils.Navigation
import com.production.smsbankinganalitics.view.screens.SplashScreen
import com.production.smsbankinganalitics.view.screens.analytics.AnalyticsScreen
import com.production.smsbankinganalitics.view.screens.intro.IntroScreen
import com.production.smsbankinganalitics.view.screens.settings.SettingsScreen
import com.production.smsbankinganalitics.view.screens.sms_banking.SmsBankingScreen
import com.production.smsbankinganalitics.view_models.SettingsViewModel
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavHost(
    settingsViewModel: SettingsViewModel,
    uiEffectsViewModel: UiEffectsViewModel,
    innerPadding: PaddingValues,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Navigation.Splash.route,
        builder = {
            composable(Navigation.Splash.route) {
                SplashScreen(navHostController)
            }
            composable(Navigation.Intro.route) {
                IntroScreen(navHostController, settingsViewModel)
            }
            composable(BottomNavBarItem.SmsBankingItem.route) {
                SmsBankingScreen(settingsViewModel.state.smsAddress, uiEffectsViewModel)
            }
            composable(BottomNavBarItem.OperationsItem.route) {
                AnalyticsScreen(
                    settingsViewModel.state.smsAddress,
                    navHostController,
                    uiEffectsViewModel
                )
            }
            composable(BottomNavBarItem.SettingsItem.route) {
                SettingsScreen(
                    navHostController,
                    settingsViewModel,
                    uiEffectsViewModel
                )
            }
        }
    )
}


