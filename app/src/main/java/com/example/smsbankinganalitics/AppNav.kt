package com.example.smsbankinganalitics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smsbankinganalitics.model.BottomNavBarItem
import com.example.smsbankinganalitics.model.Navigation
import com.example.smsbankinganalitics.view.screens.SplashScreen
import com.example.smsbankinganalitics.view.screens.analytics.AnalyticsScreen
import com.example.smsbankinganalitics.view.screens.intro.IntroScreen
import com.example.smsbankinganalitics.view.screens.settings.SettingsScreen
import com.example.smsbankinganalitics.view.screens.sms_banking.SmsBankingScreen
import com.example.smsbankinganalitics.view_models.ThemeViewModel
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainNavHost(
    themeViewModel: ThemeViewModel,
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
                IntroScreen(navHostController)
            }
            composable(BottomNavBarItem.SmsBankingItem.route) {
                SmsBankingScreen(uiEffectsViewModel)
            }
            composable(BottomNavBarItem.OperationsItem.route) {
                AnalyticsScreen(navHostController, uiEffectsViewModel)
            }
            composable(BottomNavBarItem.SettingsItem.route) {
                SettingsScreen(navHostController, themeViewModel)
            }
        }
    )
}


