package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.model.SmsAddress
import com.example.smsbankinganalitics.view_models.SettingsEvent
import com.example.smsbankinganalitics.view_models.SettingsViewModel
import com.example.smsbankinganalitics.view_models.utils.Localization

@Composable
fun SettingsBankItem(
    settingsViewModel: SettingsViewModel,
) {
    val selectedBank = remember {
        mutableStateOf(settingsViewModel.state.smsAddress)
    }
    Card(
        modifier = Modifier
            .padding(
                vertical = 6.dp,
                horizontal = 2.dp
            ),
//            .fillMaxHeight(0.7f),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.tertiary.copy(0.9f)
                )
                .padding(20.dp)
                .fillMaxWidth(),

            ) {
            item {
                Text(
                    Localization.withComposable(R.string.bank_type),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    fontSize = 20.sp,
                )
            }
            items(SmsAddress.entries.size) { index ->
                ElevatedButton(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(40),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.elevatedButtonColors().copy(
                        containerColor = if (SmsAddress.entries[index] == selectedBank.value)
                            MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.secondary
                    ),
                    onClick = {
                        settingsViewModel.onEvent(SettingsEvent.SetSmsBankAddress(SmsAddress.entries[index]))
                        selectedBank.value = SmsAddress.entries[index]
                    }
                ) {
                    Row {
                        Image(
                            painterResource(id = SmsAddress.entries[index].resId),
                            contentDescription = SmsAddress.entries[index].resId.toString(),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Text(
                            SmsAddress.entries[index].name,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiary
                            ),
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}