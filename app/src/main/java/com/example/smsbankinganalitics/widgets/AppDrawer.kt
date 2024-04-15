package com.example.smsbankinganalitics.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.ui.theme.Palette1
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
                Column(
                 modifier = Modifier
                     .fillMaxSize()
                     .background(MaterialTheme.colorScheme.onTertiary)

                ) {
                    DrawerHeader()
                    Spacer(modifier = Modifier
                        .height(15.dp)
                    )
                    DrawerElement()
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.onTertiary,
                        ),
                        label = {

                        },
                        selected = true,
                        icon = {

                        },
                        onClick = {
                            uiEffectsViewModel.onEvent(UiEffectsEvent.HideBottomBar(false))
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }

            }
        },
        content = content
    )
}