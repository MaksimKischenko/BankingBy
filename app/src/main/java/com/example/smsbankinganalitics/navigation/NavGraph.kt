package com.example.smsbankinganalitics.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smsbankinganalitics.models.BottomNavBarItem
import com.example.smsbankinganalitics.screens.OperationsItemScreen
import com.example.smsbankinganalitics.screens.SettingsScreen
import com.example.smsbankinganalitics.screens.sms_banking.SmsScreen
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavGraphBody(
    uiEffectsViewModel: UiEffectsViewModel,
    innerPadding: PaddingValues,
    activity: Activity,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavBarItem.SmsBankingItem.route,
        builder = {
            composable(BottomNavBarItem.SmsBankingItem.route) {
                SmsScreen(uiEffectsViewModel)
            }
            composable(BottomNavBarItem.OperationsItem.route) {
                OperationsItemScreen()
            }
            composable(BottomNavBarItem.SettingsItem.route) {
                SettingsScreen()
            }
        }
    )
}