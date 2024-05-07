package com.production.smsbankinganalitics.view.widgets

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

@Composable
fun DrawerElement(
    imageId: Int,
    header: String,
    content: String,
) {
    ElevatedCard(
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        ListItem(
            colors = ListItemColors(
                containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
                headlineColor = MaterialTheme.colorScheme.onTertiary,
                leadingIconColor = MaterialTheme.colorScheme.primary,
                overlineColor = MaterialTheme.colorScheme.onTertiary,
                supportingTextColor = MaterialTheme.colorScheme.onTertiary,
                trailingIconColor = MaterialTheme.colorScheme.onTertiary,
                disabledHeadlineColor = MaterialTheme.colorScheme.onTertiary,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onTertiary
            ),
            leadingContent = {
//                Image(
//                    painterResource(id = imageId),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(36.dp)
//                )
            },
            overlineContent = {

            },
            headlineContent = {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = content,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                    ),
                    fontSize = 16.sp,
                )
            },
        )
    }
}