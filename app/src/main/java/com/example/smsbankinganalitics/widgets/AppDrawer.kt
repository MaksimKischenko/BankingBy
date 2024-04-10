package com.example.smsbankinganalitics.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.view_models.UiEffectsEvent
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AppDrawer(
    uiEffectsViewModel: UiEffectsViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(end = 48.dp)
            ) {

//                Image(
//                    painter = painterResource(id = R.drawable.img),
//                    contentDescription = "Jedi",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(150.dp),
//                    contentScale = ContentScale.Crop
//                )
                Spacer(modifier = Modifier.height(15.dp))
                NavigationDrawerItem(
                    label = {

                    },
                    selected = true,
                    icon = {

                    },
                    onClick = {
                        uiEffectsViewModel.onEvent(UiEffectsEvent.ShowingDrawerEvent(false))
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        },
        content = content
    )
}