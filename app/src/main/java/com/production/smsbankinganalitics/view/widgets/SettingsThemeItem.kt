package com.production.smsbankinganalitics.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.model.AppTheme
import com.production.smsbankinganalitics.view_models.SettingsEvent
import com.production.smsbankinganalitics.view_models.SettingsViewModel
import com.production.smsbankinganalitics.view_models.utils.Localization

@Composable
fun SettingsThemeItem(
    settingsViewModel: SettingsViewModel
) {

    var checked by remember { mutableStateOf(settingsViewModel.state.theme == AppTheme.Dark) }

    ElevatedCard(
        modifier = Modifier.padding(
            horizontal = 2.dp
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.tertiary.copy(0.9f)
                )
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clip(
                        RoundedCornerShape(24.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primary.copy(0.5f)
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.light),
                    contentDescription = "dollar_circle",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                Localization.withComposable(R.string.isDart),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                fontSize = 20.sp,
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
                        settingsViewModel.onEvent(SettingsEvent.ChangeTheme(AppTheme.Dark))
                    } else {
                        settingsViewModel.onEvent(SettingsEvent.ChangeTheme(AppTheme.Light))
                    }

                }
            )
        }
    }


}