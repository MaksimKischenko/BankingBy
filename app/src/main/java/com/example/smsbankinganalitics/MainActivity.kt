package com.example.smsbankinganalitics

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.smsbankinganalitics.ui.theme.SmsBankingAnalyticsTheme
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Scaffold
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.smsbankinganalitics.navigation.BottomNavigationBar
import com.example.smsbankinganalitics.navigation.NavGraphBody
import com.example.smsbankinganalitics.services.PermissionListener
import com.example.smsbankinganalitics.view_models.ThemeViewModel
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionListener.onRequestPermissionsResult(this)
        setContent {
            AppWrapper()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun AppWrapper(
        themeViewModel: ThemeViewModel = hiltViewModel()
    ) {
        SmsBankingAnalyticsTheme(
            appTheme = themeViewModel.stateApp.theme
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                MainActivityBody(
                    this, themeViewModel
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun MainActivityBody(
        activity: Activity,
        themeViewModel: ThemeViewModel,
        uiEffectsViewModel: UiEffectsViewModel = hiltViewModel()
    ) {
        val navController = rememberNavController()
        val isVisibleBottomBar = !(uiEffectsViewModel.stateApp.isUnVisibleBottomBar ?: true)
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isVisibleBottomBar,
                    enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween()),
                    exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween())
                ) {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) { innerPadding ->
            NavGraphBody(
                themeViewModel,
                uiEffectsViewModel,
                innerPadding,
                navHostController = navController,
            )
        }
    }
}