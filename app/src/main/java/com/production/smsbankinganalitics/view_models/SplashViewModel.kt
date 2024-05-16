package com.production.smsbankinganalitics.view_models

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.production.smsbankinganalitics.view_models.data.PreferencesManager
import com.production.smsbankinganalitics.view_models.data.PrefsKeys
import com.production.smsbankinganalitics.view_models.utils.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModel() {

    @Composable
    fun initPermissionsLauncherAndCheckFirstLoad(navHostController: NavHostController, context: Context)
            : ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> {
        return rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { result: Map<String, Boolean> ->
            if (result.all { it.value }) {
                onFirstLoad(navHostController)
            } else {
                setPermissionsByDetailSettings(context)
            }
        }
    }

    private fun setPermissionsByDetailSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.setData(uri)
        context.startActivity(intent)
        (context as Activity).finish()
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


