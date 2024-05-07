package com.production.smsbankinganalitics

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.production.smsbankinganalitics.view.theme.SmsBankingAnalyticsTheme
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Scaffold
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.production.smsbankinganalitics.view.widgets.BottomNavigationBar
import com.production.smsbankinganalitics.view_models.SettingsViewModel
import com.production.smsbankinganalitics.view_models.UiEffectsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppWrapper()
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun AppWrapper(
        settingsViewModel: SettingsViewModel = hiltViewModel()
    ) {
        SmsBankingAnalyticsTheme(
            appTheme = settingsViewModel.state.theme
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                MainActivityBody(settingsViewModel)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun MainActivityBody(
        settingsViewModel: SettingsViewModel,
        uiEffectsViewModel: UiEffectsViewModel = hiltViewModel()
    ) {
        val navHostController = rememberNavController()
        val isVisibleBottomBar = !(uiEffectsViewModel.state.isUnVisibleBottomBar ?: true)
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isVisibleBottomBar,
                    enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween()),
                    exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween())
                ) {
                    BottomNavigationBar(navHostController = navHostController)
                }
            }
        ) { innerPadding ->
            AppNavHost(
                settingsViewModel,
                uiEffectsViewModel,
                innerPadding,
                navHostController = navHostController,
            )

        }
    }
}
