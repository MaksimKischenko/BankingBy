package com.example.smsbankinganalitics.view.screens.settings


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.view_models.ThemeViewModel
import com.example.smsbankinganalitics.view.widgets.SettingsAppBar


@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    themeViewModel: ThemeViewModel,
) {

    Scaffold(
        topBar = {
            SettingsAppBar(
                navHostController
            )
        }
    ) {
        SettingsBodyScreen(
            it,themeViewModel
        )

    }

}


