package com.production.smsbankinganalitics.view.widgets


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.production.smsbankinganalitics.view_models.utils.BottomNavBarItem
import com.production.smsbankinganalitics.view_models.utils.Localization


@Composable
fun BottomNavigationBar(
    navHostController: NavHostController
) {
    val items = remember {
        listOf(
            BottomNavBarItem.SmsBankingItem,
            BottomNavBarItem.OperationsItem,
            BottomNavBarItem.SettingsItem,
        )
    }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(
                RoundedCornerShape(10)
            ),
        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
        tonalElevation = 10.dp
    ) {
        val backStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                ),
                onClick = {
                    if (item.route != currentRoute) {
                        navHostController.navigate(item.route)
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = ImageVector.vectorResource(item.iconId),
                        contentDescription = item.iconId.toString(),
                    )
                },
                label = {
                    Text(
                        Localization.withComposable(item.resId),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                    )
                },
            )
        }
    }
}




