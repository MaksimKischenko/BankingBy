package com.production.smsbankinganalitics.view.screens.settings


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.production.smsbankinganalitics.view.widgets.SettingsAppBar
import com.production.smsbankinganalitics.view_models.SettingsViewModel
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel


@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    settingsViewModel: SettingsViewModel,
    uiEffectsViewModel: UiEffectsViewModel
) {

    Scaffold(
        topBar = {
            SettingsAppBar(
                navHostController
            )
        }
    ) {
        SettingsBodyScreen(
            it,
            settingsViewModel,
        )
    }
}


