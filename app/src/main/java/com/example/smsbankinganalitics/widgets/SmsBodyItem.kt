package com.example.smsbankinganalitics.widgets

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.ui.theme.Coral
import com.example.smsbankinganalitics.ui.theme.Palette1
import com.example.smsbankinganalitics.ui.theme.Palette3
import com.example.smsbankinganalitics.ui.theme.SageGreen
import com.example.smsbankinganalitics.utils.DateFormatters
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SmsBodyItem(
    smsBody: SmsParsedBody?,
    context: Context = LocalContext.current,
) {
    ListItem(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 12.dp)
            .clip(
                RoundedCornerShape(15)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(15)

            ),
        shadowElevation = 4.dp,
        colors = ListItemColors(
            containerColor = MaterialTheme.colorScheme.onTertiary,
            headlineColor = MaterialTheme.colorScheme.tertiary,
            leadingIconColor = MaterialTheme.colorScheme.primary,
            overlineColor = MaterialTheme.colorScheme.tertiary,
            supportingTextColor = MaterialTheme.colorScheme.tertiary,
            trailingIconColor = actionCategoryColor(
                smsBody?.actionCategory ?: ActionCategory.UNKNOWN
            ),
            disabledHeadlineColor = MaterialTheme.colorScheme.tertiary,
            disabledLeadingIconColor = MaterialTheme.colorScheme.tertiary,
            disabledTrailingIconColor = MaterialTheme.colorScheme.tertiary
        ),
        leadingContent = {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.sms_read),
                contentDescription = R.drawable.sms_read.toString(),
//                tint = MaterialTheme.colorScheme.tertiary
            )
        },
        overlineContent = {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = smsBody?.terminal?.noAssociatedName?.trim()
                    ?: "",  //smsBody?.paymentCurrency?:"",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.colorScheme.tertiary
                ),
                fontSize = 14.sp,
            )

        },
        headlineContent = {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = smsBody?.paymentDate ?: "",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.colorScheme.tertiary,
                ),
                fontSize = 12.sp,
            )
        },
        supportingContent = {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = smsBody?.paymentSum.toString() + " " + smsBody?.paymentCurrency,
                style = TextStyle(
//                    color = actionCategoryColor(smsBody?.actionCategory ?: ActionCategory.UNKNOWN),
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        },
        trailingContent = {
            Text(
                text = context.getString(smsBody?.actionCategory?.resId ?: 0),
                style = TextStyle(
                    color = actionCategoryColor(smsBody?.actionCategory ?: ActionCategory.UNKNOWN),
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 16.sp,
            )
        },
    )
}

fun actionCategoryColor(actionCategory: ActionCategory): Color {
    return when (actionCategory) {
        ActionCategory.PAYMENT -> Coral
        ActionCategory.AVAILABLE -> Palette1
        ActionCategory.TRANSFER_FROM -> Coral
        ActionCategory.UNKNOWN -> Palette3
        ActionCategory.TRANSFER_TO -> SageGreen
    }
}