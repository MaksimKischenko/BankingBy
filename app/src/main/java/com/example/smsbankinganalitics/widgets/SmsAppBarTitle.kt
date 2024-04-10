package com.example.smsbankinganalitics.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.utils.DateUtils
import com.example.smsbankinganalitics.view_models.SMSReceiverState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SmsAppBarTitle(
    smsReceiverViewModelState: SMSReceiverState
) {

    val formattedDate = smsReceiverViewModelState.smsReceivedList?.lastOrNull()?.paymentDate?.let {
        DateUtils.fromLocalDateTimeToStringDate(
            it
        )
    }

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(id = R.string.from) + " $formattedDate ", maxLines = 1, overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}