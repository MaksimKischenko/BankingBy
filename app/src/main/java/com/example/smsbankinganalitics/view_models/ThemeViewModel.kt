package com.example.smsbankinganalitics.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smsbankinganalitics.data.PreferencesManager
import com.example.smsbankinganalitics.data.PrefsKeys
import com.example.smsbankinganalitics.models.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class ThemeViewModelFactory @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val prefs: PreferencesManager
): ViewModel() {
    var stateApp by mutableStateOf(ThemeState(loadTheme()))

    fun onEvent(event: ThemeEvent) {
        when(event) {
            is ThemeEvent.ThemeChange -> {
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
    data class ThemeChange(val theme: AppTheme): ThemeEvent()
}

data class ThemeState(
    val theme: AppTheme,
)