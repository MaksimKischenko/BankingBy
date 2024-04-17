package com.example.smsbankinganalitics.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.ui.theme.Palette7
import com.example.smsbankinganalitics.ui.theme.Palette1
import com.example.smsbankinganalitics.ui.theme.Palette3
import com.example.smsbankinganalitics.ui.theme.Palette6
import com.example.smsbankinganalitics.utils.DateUtils
import com.example.smsbankinganalitics.utils.Localization

@Composable
fun SmsBodyItem(
    smsBody: SmsParsedBody?,
) {
    ElevatedCard(
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        ListItem(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(16)
                )
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(16)

                ),
            shadowElevation = 10.dp,
            colors = ListItemColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                headlineColor = MaterialTheme.colorScheme.onTertiary,
                leadingIconColor = MaterialTheme.colorScheme.primary,
                overlineColor = MaterialTheme.colorScheme.onTertiary,
                supportingTextColor = MaterialTheme.colorScheme.onTertiary,
                trailingIconColor = actionCategoryColor(
                    smsBody?.actionCategory ?: ActionCategory.UNKNOWN
                ),
                disabledHeadlineColor = MaterialTheme.colorScheme.onTertiary,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onTertiary
            ),
            leadingContent = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.sms_read),
                    contentDescription = R.drawable.sms_read.toString(),
                )
            },
            overlineContent = {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = smsBody?.terminal?.noAssociatedName?.trim()
                        ?: "",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                    ),
                    fontSize = 14.sp,
                )
            },
            headlineContent = {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = smsBody?.paymentDate?.let { DateUtils.fromLocalDateTimeToStringDate(it) }?:"",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                    ),
                    fontSize = 12.sp,
                )
            },
            supportingContent = {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = smsBody?.paymentSum.toString() + " " + smsBody?.paymentCurrency,
                    style = TextStyle(
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            },
            trailingContent = {
                Text(
                    Localization.withComposable(smsBody?.actionCategory?.resId ?: 0),
                    style = TextStyle(
                        color = actionCategoryColor(smsBody?.actionCategory ?: ActionCategory.UNKNOWN),
                        fontWeight = FontWeight.Bold,
                    ),
                    fontSize = 16.sp,
                )
            },
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