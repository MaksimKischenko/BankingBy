package com.example.smsbankinganalitics.view.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.model.NavBarItem
import com.example.smsbankinganalitics.view_models.UiEffectsEvent
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsAppBar(
    uiEffectsViewModel: UiEffectsViewModel,
    appBarTitleName: MutableState<String>,
    navController: NavHostController
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(0, 0, 10, 10)
            ),
        title = {
            Text(text = appBarTitleName.value)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                contentDescription = "bluetoothSearching"
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clickable {
                        uiEffectsViewModel.onEvent(UiEffectsEvent.HideBottomBar(false))
                        navController.navigate(NavBarItem.SettingsItem.route)
                    }
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.settings_gear),
                contentDescription = "bluetoothSearching"
            )
        }
    )
}