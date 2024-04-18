package com.example.smsbankinganalitics.view.widgets


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.smsbankinganalitics.view_models.SmsReceiverState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsAppBar(
    smsReceiverViewModelState: SmsReceiverState,
    onFilterClick: () -> Unit,
    onDrawerClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(0, 0, 10, 10)
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
                    .padding(8.dp)
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_dehaze),
                contentDescription = "bluetoothSearching",
                tint = MaterialTheme.colorScheme.tertiary
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
                    .padding(8.dp)
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.filter),
                contentDescription = "bluetoothSearching",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    )
}

