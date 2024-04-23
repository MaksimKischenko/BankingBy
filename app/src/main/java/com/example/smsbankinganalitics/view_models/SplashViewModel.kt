package com.example.smsbankinganalitics.view_models

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.smsbankinganalitics.model.IntroPageItem
import com.example.smsbankinganalitics.model.Navigation
import com.example.smsbankinganalitics.view_models.data.PreferencesManager
import com.example.smsbankinganalitics.view_models.data.PrefsKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModel() {



    @Composable
    fun initLauncherAndCheckFirstLoad(navHostController: NavHostController)
            : ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> {
        return rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result: Map<String, Boolean> ->
            if (result.all { it.value }) {
                onFirstLoad(navHostController)
            } else {
//            navHostController.navigate("/") {
//                popUpTo("/") {
//                    inclusive = true
//                }
//            }
            }
        }
    }
    private fun onFirstLoad(navHostController: NavHostController,) {
        viewModelScope.launch(Dispatchers.Main) {
            (prefs.read(PrefsKeys.firstLoad) as Boolean).let { firstLoad ->
                when (firstLoad) {
                    true -> {
                        navHostController.navigate(Navigation.Intro.route) {
                            popUpTo(Navigation.Intro.route) {
                                inclusive = true
                            }
                        }
                        prefs.write(PrefsKeys.firstLoad, false)
                    }
                    false -> {
                        navHostController.navigate(Navigation.SmsBanking.route) {
                            popUpTo(Navigation.SmsBanking.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }

    }
}


data class IntroState(
    val introItems: List<IntroPageItem>
)