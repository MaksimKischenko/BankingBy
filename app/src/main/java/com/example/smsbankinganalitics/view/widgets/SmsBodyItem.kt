package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.model.ActionCategory
import com.example.smsbankinganalitics.model.SmsParsedBody
import com.example.smsbankinganalitics.view.theme.Palette7
import com.example.smsbankinganalitics.view.theme.Palette1
import com.example.smsbankinganalitics.view.theme.Palette3
import com.example.smsbankinganalitics.view.theme.Palette6
import com.example.smsbankinganalitics.view_models.utils.DateUtils
import com.example.smsbankinganalitics.view_models.utils.Localization

@Composable
fun SmsBodyItem(
    smsBody: SmsParsedBody?,
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
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f)
                )
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = smsBody?.paymentDate?.let {
                        DateUtils.fromLocalDateTimeToStringDate(it)
                    }
                        ?: "",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    fontSize = 18.sp,
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
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = smsBody?.paymentSum.toString() + " " + smsBody?.paymentCurrency,
                style = TextStyle(
                    color = actionCategoryColor(
                        smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                    ),
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            )
            Text(
                Localization.withComposable(smsBody?.actionCategory?.resId ?: 0),
                style = TextStyle(
                    color = actionCategoryColor(
                        smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                    ),
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 22.sp,
            )
        }
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