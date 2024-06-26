package com.production.smsbankinganalitics.view.widgets

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
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.UiEffectsEvent
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel
import com.production.smsbankinganalitics.view_models.utils.Navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsAppBar(
    uiEffectsViewModel: UiEffectsViewModel,
    appBarTitleName: MutableState<String>,
    navHostController: NavHostController
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
                        Navigation.goToSmsBanking(navHostController, isFirstLoad = false)
                    }
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                contentDescription = "arrow_back",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clickable {
                        uiEffectsViewModel.onEvent(UiEffectsEvent.HideBottomBar(false))
                        Navigation.goToSettings(navHostController, isFirstLoad = false)
                    }
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.settings_gear),
                contentDescription = "settings_gear",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    )
}