package com.example.smsbankinganalitics.view_models

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.view_models.utils.Navigation
import com.example.smsbankinganalitics.view_models.data.PreferencesManager
import com.example.smsbankinganalitics.view_models.data.PrefsKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModel() {


    @Composable
    fun initPermissionsLauncherAndCheckFirstLoad(navHostController: NavHostController)
            : ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> {
        return rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result: Map<String, Boolean> ->
            if (result.all { it.value }) {
                onFirstLoad(navHostController)
            } else {

            }
        }
    }

    private fun onFirstLoad(navHostController: NavHostController) {
        (prefs.read(PrefsKeys.firstLoad) as Boolean).let { firstLoad ->
            when (firstLoad) {
                true -> {
                    Navigation.goToIntro(navHostController)
                    prefs.write(PrefsKeys.firstLoad, false)
                }

                false -> {
                    Navigation.goToSmsBanking(navHostController, isFirstLoad = true)
                }
            }
        }
    }
}


