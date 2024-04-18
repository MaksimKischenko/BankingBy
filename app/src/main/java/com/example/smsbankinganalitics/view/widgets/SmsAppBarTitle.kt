package com.example.smsbankinganalitics.view.widgets


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.view_models.utils.Localization
import com.example.smsbankinganalitics.view_models.SmsReceiverState

@Composable
fun SmsAppBarTitle(
    smsReceiverViewModelState: SmsReceiverState
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                Localization.withComposable(R.string.from) + " ${smsReceiverViewModelState.smsCommonInfo?.dateFrom} ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}