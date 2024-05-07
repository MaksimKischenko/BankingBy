package com.production.smsbankinganalitics.view.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.model.ActionCategory
import com.production.smsbankinganalitics.model.SmsParsedBody
import com.production.smsbankinganalitics.view.theme.Palette7
import com.production.smsbankinganalitics.view.theme.Palette1
import com.production.smsbankinganalitics.view.theme.Palette3
import com.production.smsbankinganalitics.view.theme.Palette6
import com.production.smsbankinganalitics.view_models.utils.DateUtils
import com.production.smsbankinganalitics.view_models.utils.Localization

@Composable
fun SmsBodyItem(
    smsBody: SmsParsedBody?,
) {
    Card(
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 4.dp
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            ActionCategoryRow(smsBody)
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = smsBody?.paymentSum.toString() + " " + smsBody?.paymentCurrency,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                fontWeight = FontWeight.Medium,
                fontSize = 28.sp,
            )
            Text(
                text = smsBody?.terminal?.noAssociatedName?.trim()
                    ?: "",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary
                ),
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun ActionCategoryRow(smsBody: SmsParsedBody?) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .background(
                    actionCategoryColor(
                        smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                    ).copy(0.4f)
                )
        ) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                imageVector = ImageVector.vectorResource(R.drawable.dollar_circle),
                contentDescription = R.drawable.dollar_circle.toString(),
                tint = actionCategoryColor(
                    smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                ),
            )
        }
        Text(
            Localization.withComposable(smsBody?.actionCategory?.resId ?: 0),
            style = TextStyle(
                color = actionCategoryColor(
                    smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                ),
                fontWeight = FontWeight.Medium
            ),
            fontSize = 22.sp,
        )
        Text(
            text = smsBody?.paymentDate?.let {
                DateUtils.fromLocalDateTimeToStringDate(it)
            } ?: "",

            style = TextStyle(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onTertiary
            ),
            fontSize = 18.sp,
        )

    }
}

fun actionCategoryColor(actionCategory: ActionCategory): Color {
    return when (actionCategory) {
        ActionCategory.PAYMENT -> Palette7
        ActionCategory.AVAILABLE -> Palette1
        ActionCategory.TRANSFER_FROM -> Palette7
        ActionCategory.UNKNOWN -> Palette3
        ActionCategory.TRANSFER_TO -> Palette6
    }
}