package com.production.smsbankinganalitics.view.widgets

import androidx.compose.foundation.border
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.view_models.utils.Localization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAppBar(
    navHostController: NavHostController
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
        title = {
            Text(Localization.withComposable(R.string.settings))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        navHostController.popBackStack()
                    }
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                contentDescription = "arrow_back"
            )
        }
    )
}