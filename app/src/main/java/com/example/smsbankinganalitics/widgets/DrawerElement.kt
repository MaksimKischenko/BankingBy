package com.example.smsbankinganalitics.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.utils.DateUtils
import com.example.smsbankinganalitics.utils.Localization

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
            leadingContent = {
                Image(
                    painterResource(id = imageId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                )
            },
            overlineContent = {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = header,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                    ),
                    fontSize = 16.sp,
                )
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
//            supportingContent = {
//                Text(
//                    modifier = Modifier.padding(vertical = 6.dp),
//                    text = "Остаток: 2400 BYN",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Medium,
//                    ),
//                    fontSize = 16.sp,
//                )
//            },
//            trailingContent = {
//                Text(
//                    modifier = Modifier.padding(vertical = 6.dp),
//                    text = "BYN",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Medium,
//                    ),
//                    fontSize = 16.sp,
//                )
//            },
        )
    }
}