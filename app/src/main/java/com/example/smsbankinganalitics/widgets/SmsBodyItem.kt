package com.example.smsbankinganalitics.widgets

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.utils.DateFormatters
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SmsBodyItem(
    smsBody: SmsParsedBody?,
    context:Context = LocalContext.current,

) {
    ListItem(
        leadingContent = {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.sms_read),
                contentDescription = R.drawable.sms_read.toString(),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        overlineContent = {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = smsBody?.terminal?.noAssociatedName?.trim()?:"",  //smsBody?.paymentCurrency?:"",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary
                ),
                fontSize = 14.sp,
            )

        },
        headlineContent = {
            Text(
                text = context.getString(smsBody?.actionCategory?.resId?:0),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 16.sp,
            )
        },
        supportingContent = {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = smsBody?.paymentSum.toString() + " " + smsBody?.paymentCurrency,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        },
        trailingContent = {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = smsBody?.paymentDate?.format(DateFormatters.dateOnly)?:"",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary,
                ),
                fontSize = 12.sp,
            )
        },

    )
}