package com.example.smsbankinganalitics.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.smsbankinganalitics.models.AppTheme
import com.example.smsbankinganalitics.view_models.ThemeEvent
import com.example.smsbankinganalitics.view_models.ThemeViewModel

@Composable
fun SettingsBodyScreen(
    innerPadding: PaddingValues,
    themeViewModel: ThemeViewModel
) {


    var checked by remember { mutableStateOf(themeViewModel.stateApp.theme != AppTheme.Light) }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .padding(innerPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Switch(
//        thumbContent = {
//         Text(text = "Сменить тему")
//        },
            checked = checked,
            colors = SwitchDefaults.colors().copy(
                uncheckedThumbColor = MaterialTheme.colorScheme.onTertiary,
                uncheckedTrackColor = MaterialTheme.colorScheme.tertiary,
                uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                uncheckedIconColor = MaterialTheme.colorScheme.onTertiary,
                checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
                checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                checkedBorderColor = MaterialTheme.colorScheme.primary,
                checkedIconColor = MaterialTheme.colorScheme.onTertiary,
            ),
            onCheckedChange = {
                checked = it
                if (checked) {
                    themeViewModel.onEvent(ThemeEvent.Change(AppTheme.Dark))
                } else {
                    themeViewModel.onEvent(ThemeEvent.Change(AppTheme.Light))
                }

            }
        )
    }

}