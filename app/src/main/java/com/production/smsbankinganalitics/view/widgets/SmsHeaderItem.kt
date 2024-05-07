package com.production.smsbankinganalitics.view.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.model.Currencies
import com.production.smsbankinganalitics.view_models.SmsReceiverState
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel
import com.production.smsbankinganalitics.view_models.utils.Localization

@Composable
fun SmsHeaderItem(
    smsReceiverViewModelState: SmsReceiverState,
    uiEffectsViewModel: UiEffectsViewModel
) {
    Box(
        modifier = Modifier.clip(
            RoundedCornerShape(0, 0, 10, 10)
        ),
    ) {
        Row(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary
                )
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            ) {
                AvailableBalanceRow()
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = smsReceiverViewModelState.smsCommonInfo?.availableAmount.toString() + " " + Currencies.BYN.name,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.tertiary.copy(0.7f),
                        fontWeight = FontWeight.Bold

                    ),
                    fontSize = 22.sp,
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clip(
                        RoundedCornerShape(24.dp)
                    )
                    .background(
                        uiEffectsViewModel.shimmerBrush(
                            MaterialTheme.colorScheme.tertiary,
                        )
                    )
            ) {
                Image(
                    painterResource(smsReceiverViewModelState.smsCommonInfo?.resId ?: 0),
                    contentDescription = smsReceiverViewModelState.smsCommonInfo?.resId.toString(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)
                )
            }

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
                    MaterialTheme.colorScheme.secondary.copy(0.5f)
                )
        ) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp),
                imageVector = ImageVector.vectorResource(R.drawable.wallet),
                contentDescription = "wallet",
                tint = MaterialTheme.colorScheme.tertiary.copy(0.7f),
            )
        }
        Text(
            Localization.withComposable(R.string.available ?: 0),
            modifier = Modifier.padding(horizontal = 6.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.tertiary.copy(0.7f),

                ),
            fontSize = 21.sp,
        )
    }
}