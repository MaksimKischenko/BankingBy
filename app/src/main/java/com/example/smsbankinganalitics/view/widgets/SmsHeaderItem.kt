package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.model.Currencies
import com.example.smsbankinganalitics.view.theme.Palette14
import com.example.smsbankinganalitics.view_models.SmsReceiverState
import com.example.smsbankinganalitics.view_models.utils.Localization

@Composable
fun SmsHeaderItem(
    smsReceiverViewModelState: SmsReceiverState,
) {
    ElevatedCard(
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 14.dp
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = smsReceiverViewModelState.smsCommonInfo?.cardMask?:"",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
            )
            AvailableBalanceRow()
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = smsReceiverViewModelState.smsCommonInfo?.availableAmount.toString() + " " + Currencies.BYN.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun AvailableBalanceRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .background(
                    Palette14.copy(0.4f)
                )
        ) {
            Icon(
                modifier = Modifier
                    .size(34.dp)
                    .padding(8.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.wallet),
                contentDescription = "dollar_circle",
                tint = Palette14
            )
        }
        Text(
            Localization.withComposable(R.string.available ?: 0),
            modifier = Modifier.padding(horizontal = 6.dp),
            style = TextStyle(
                color = Palette14,
                fontWeight = FontWeight.Thin,
            ),
            fontSize = 20.sp,
        )
    }
}