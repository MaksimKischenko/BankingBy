package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.model.AppTheme
import com.example.smsbankinganalitics.view_models.ThemeEvent
import com.example.smsbankinganalitics.view_models.ThemeViewModel

@Composable
fun SettingsThemeItem(
    themeViewModel: ThemeViewModel
) {

    var checked by remember { mutableStateOf(themeViewModel.stateApp.theme != AppTheme.Light) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Темный фон",
            style = TextStyle(
                fontWeight = FontWeight.Medium,
            ),
            fontSize = 24.sp,
        )
        Switch(
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