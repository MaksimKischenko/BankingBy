package com.example.smsbankinganalitics.view_models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smsbankinganalitics.view_models.data.PreferencesManager
import com.example.smsbankinganalitics.view_models.data.PrefsKeys
import com.example.smsbankinganalitics.model.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val prefs: PreferencesManager
): ViewModel() {
    var stateApp by mutableStateOf(ThemeState(loadTheme()))

    fun onEvent(event: ThemeEvent) {
        when(event) {
            is ThemeEvent.Change -> {
                prefs.write(PrefsKeys.selectedAppTheme, event.theme.name)
                stateApp = stateApp.copy(theme = event.theme)
            }
        }
    }

    private fun loadTheme():AppTheme {
        val themeLabel = prefs.read(PrefsKeys.selectedAppTheme) as String
        return when(themeLabel) {
            AppTheme.Default.name -> return AppTheme.Default
            AppTheme.Dark.name -> return AppTheme.Dark
            AppTheme.Light.name -> return AppTheme.Light
            else -> AppTheme.Default
        }
    }
}

sealed class ThemeEvent {
    data class Change(val theme: AppTheme): ThemeEvent()
}

data class ThemeState(
    val theme: AppTheme,
)