package com.example.smsbankinganalitics.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsAppBar(
    onFilterClick: () -> Unit,
) {
    CenterAlignedTopAppBar(modifier = Modifier
        .fillMaxWidth()
        .clip(
            RoundedCornerShape(0, 0, 15, 15)
        )
        .border(
            width = 2.dp,
            color = Color.Transparent,
            shape = RoundedCornerShape(0, 0, 15, 15)

        ), colors = TopAppBarDefaults.topAppBarColors(
        containerColor =  Color.Transparent
    ), title = {
        Text(
            "Фильтр", maxLines = 1, overflow = TextOverflow.Ellipsis, style = TextStyle(
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Medium
            )
        )
    }, actions = {
        Icon(
            modifier = Modifier
                .clickable {

                }
                .size(32.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.filter),
            contentDescription = "bluetoothSearching",

            )
    })
}

