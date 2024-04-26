package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
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

    ElevatedCard(
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 2.dp
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.tertiary.copy(0.9f)
                )
                .padding(20.dp)
                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically

        ) {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .clip(
//                        RoundedCornerShape(24.dp)
//                    )
//                    .background(
//                        MaterialTheme.colorScheme.primary.copy(0.5f)
//                    )
//            ) {
//                Icon(
//                    modifier = Modifier
//                        .size(48.dp)
//                        .padding(4.dp),
//                    imageVector = ImageVector.vectorResource(id = R.drawable.language),
//                    contentDescription = "light",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }

            Text(
                Localization.withComposable(R.string.bank_type),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                fontSize = 20.sp,
            )
            SmsAddress.entries.filter { it != SmsAddress.UNKNOWN } .map {
                ElevatedButton(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(40),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.elevatedButtonColors().copy(
                        containerColor = if (it == selectedBank.value)
                            MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.secondary
                    ),
                    onClick = {
                        settingsViewModel.onEvent(SettingsEvent.SetSmsBankAddress(it))
                        selectedBank.value = it
                    }) {
                    Row {
                        Image(
                            painterResource(id = it.resId),
                            contentDescription =  R.drawable.bnb_logo.toString(),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Text(
                            it.name,
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