package com.production.smsbankinganalitics.view.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.production.smsbankinganalitics.view.widgets.SettingsBankItem
import com.production.smsbankinganalitics.view.widgets.SettingsLangItem
import com.production.smsbankinganalitics.view.widgets.SettingsThemeItem
import com.production.smsbankinganalitics.view_models.SettingsViewModel


@Composable
fun SettingsBodyScreen(
    innerPadding: PaddingValues,
    settingsViewModel: SettingsViewModel,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .padding(innerPadding)
            .fillMaxSize()
            .padding(4.dp)
    ) {
        SettingsLangItem(settingsViewModel)
        SettingsBankItem(settingsViewModel)
        SettingsThemeItem(settingsViewModel)
    }
}

