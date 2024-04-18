package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.model.ActionCategory
import com.example.smsbankinganalitics.model.SmsParsedBody
import com.example.smsbankinganalitics.view.theme.Palette7
import com.example.smsbankinganalitics.view.theme.Palette1
import com.example.smsbankinganalitics.view.theme.Palette17
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
                .padding(16.dp),
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
        verticalAlignment = Alignment.CenterVertically
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
                imageVector = ImageVector.vectorResource(id = R.drawable.dollar_circle),
                contentDescription = "dollar_circle",
                tint = actionCategoryColor(
                    smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                ),
            )
        }
        Text(
            Localization.withComposable(smsBody?.actionCategory?.resId ?: 0),
            modifier = Modifier.padding(horizontal = 6.dp),
            style = TextStyle(
                color = actionCategoryColor(
                    smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                ),
                fontWeight = FontWeight.Thin,
            ),
            fontSize = 18.sp,
        )
        Text(
            text = smsBody?.paymentDate?.let {
                DateUtils.fromLocalDateTimeToStringDate(it)
            } ?: "",
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp),
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