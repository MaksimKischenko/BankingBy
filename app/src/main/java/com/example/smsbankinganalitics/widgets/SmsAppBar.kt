package com.example.smsbankinganalitics.widgets


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.view_models.SMSReceiverState



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsAppBar(
    smsReceiverViewModelState: SMSReceiverState,
    onFilterClick: () -> Unit,
    onDrawerClick: () -> Unit,
) {

    CenterAlignedTopAppBar(
        modifier = Modifier
        .fillMaxWidth()
        .clip(
            RoundedCornerShape(0, 0, 16, 16)
        )
        .border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(0, 0, 16, 16)
        ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        onDrawerClick.invoke()
                    }
                    .size(32.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_dehaze),
                contentDescription = "bluetoothSearching"
            )
        },
        title = {
            SmsAppBarTitle(smsReceiverViewModelState)
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clickable {
                        onFilterClick.invoke()
                    }
                    .size(32.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.calendar_month),
                contentDescription = "bluetoothSearching"
            )
        }
    )
}

