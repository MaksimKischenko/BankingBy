package com.example.smsbankinganalitics.view.screens.settings


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.view.widgets.SettingsAppBar
import com.example.smsbankinganalitics.view_models.SettingsViewModel


@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    settingsViewModel: SettingsViewModel,
) {

    Scaffold(
        topBar = {
            SettingsAppBar(
                navHostController
            )
        }
    ) {
        SettingsBodyScreen(
            it,settingsViewModel
        )

    }

}


