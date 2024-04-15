package com.example.smsbankinganalitics.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.utils.DateUtils

@Composable
fun DrawerElement() {
    ElevatedCard(
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        ListItem(
            colors = ListItemColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                headlineColor = MaterialTheme.colorScheme.onTertiary,
                leadingIconColor = MaterialTheme.colorScheme.primary,
                overlineColor = MaterialTheme.colorScheme.onTertiary,
                supportingTextColor = MaterialTheme.colorScheme.onTertiary,
                trailingIconColor = MaterialTheme.colorScheme.onTertiary,
                disabledHeadlineColor = MaterialTheme.colorScheme.onTertiary,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onTertiary
            ),
            headlineContent = {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = "",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                    ),
                    fontSize = 12.sp,
                )
            }
        )
    }
}