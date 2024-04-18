package com.example.smsbankinganalitics.view.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.model.AppTheme
import com.example.smsbankinganalitics.view.widgets.SettingsLangSettings
import com.example.smsbankinganalitics.view.widgets.SettingsThemeItem
import com.example.smsbankinganalitics.view_models.ThemeEvent
import com.example.smsbankinganalitics.view_models.ThemeViewModel

@Composable
fun SettingsBodyScreen(
    innerPadding: PaddingValues,
    themeViewModel: ThemeViewModel
) {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .padding(innerPadding)
            .fillMaxSize()
            .padding(24.dp)
    ) {

        SettingsThemeItem(themeViewModel)
        SettingsLangSettings()
    }
}

