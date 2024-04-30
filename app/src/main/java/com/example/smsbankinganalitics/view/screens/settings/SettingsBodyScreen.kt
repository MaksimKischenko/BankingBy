package com.example.smsbankinganalitics.view.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.view.widgets.SettingsBankItem
import com.example.smsbankinganalitics.view.widgets.SettingsLangItem
import com.example.smsbankinganalitics.view.widgets.SettingsThemeItem
import com.example.smsbankinganalitics.view.widgets.SmsBodyItem
import com.example.smsbankinganalitics.view_models.SettingsViewModel
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel


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

