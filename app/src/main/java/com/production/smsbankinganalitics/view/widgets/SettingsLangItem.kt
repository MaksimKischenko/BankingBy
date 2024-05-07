package com.production.smsbankinganalitics.view.widgets

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.model.LanTags
import com.production.smsbankinganalitics.view_models.SettingsEvent
import com.production.smsbankinganalitics.view_models.SettingsViewModel

@Composable
fun SettingsLangItem(
    settingsViewModel: SettingsViewModel,
    context: Context = LocalContext.current,
) {

    val selectedLang = remember {
        mutableStateOf(settingsViewModel.state.selectedLang.tag)
    }

    Card(
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
                    imageVector = ImageVector.vectorResource(id = R.drawable.language),
                    contentDescription = "light",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            LanTags.entries.map {
                ElevatedButton(
                    shape = RoundedCornerShape(40),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.elevatedButtonColors().copy(
                        containerColor = if (it.tag == selectedLang.value)
                            MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.secondary
                    ),
                    onClick = {
                        settingsViewModel.onEvent(SettingsEvent.ChangeLang(it, context))
                        selectedLang.value = it.tag
                    }) {
                    Text(
                        it.name,
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                        ,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiary
                        ),
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

