package com.example.smsbankinganalitics

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.smsbankinganalitics.ui.theme.SmsBankingAnalyticsTheme
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.smsbankinganalitics.data.PreferencesManager
import com.example.smsbankinganalitics.navigation.BottomNavigationBar
import com.example.smsbankinganalitics.navigation.NavGraphBody
import com.example.smsbankinganalitics.services.PermissionListener
import com.example.smsbankinganalitics.view_models.ThemeViewModel
import com.example.smsbankinganalitics.view_models.ThemeViewModelFactory


import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefs: PreferencesManager
    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(prefs)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionListener.onRequestPermissionsResult(this@MainActivity)
        setContent {
            SmsBankingAnalyticsTheme(
                appTheme = themeViewModel.stateApp.theme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainActivityBody(this@MainActivity)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun MainActivityBody(
        activity: Activity,
    ) {
        val navController = rememberNavController()
        Scaffold(bottomBar = {
            BottomNavigationBar(navController = navController)
        }) { innerPadding ->
            NavGraphBody(
                innerPadding,
                activity,
                navHostController = navController,
            )
        }
    }
}