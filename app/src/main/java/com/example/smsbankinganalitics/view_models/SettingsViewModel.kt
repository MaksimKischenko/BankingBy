package com.example.smsbankinganalitics.view_models

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smsbankinganalitics.view_models.data.PreferencesManager
import com.example.smsbankinganalitics.view_models.data.PrefsKeys
import com.example.smsbankinganalitics.model.AppTheme
import com.example.smsbankinganalitics.model.LanTags
import com.example.smsbankinganalitics.model.SmsAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: PreferencesManager
) : ViewModel() {
    var state by mutableStateOf(SettingsState(loadTheme(), loadSmsAddress(), loadSelectedLang()))

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeTheme -> {
                prefs.write(PrefsKeys.selectedAppTheme, event.theme.name)
                state = state.copy(theme = event.theme)
            }

            is SettingsEvent.SetSmsBankAddress -> {
                prefs.write(PrefsKeys.selectedSmsAddress, event.smsAddress.name)
                state = state.copy(
                    smsAddress = event.smsAddress
                )
            }

            is SettingsEvent.ChangeLang -> {
                viewModelScope.launch(Dispatchers.IO) {
                    langSelection(event.context, event.selectedLang.tag)
                    state = state.copy(
                        selectedLang = event.selectedLang
                    )
                }
            }
        }
    }

    private fun loadTheme(): AppTheme {
        val themeLabel = prefs.read(PrefsKeys.selectedAppTheme) as String
        return when (themeLabel) {
            AppTheme.Default.name -> return AppTheme.Default
            AppTheme.Dark.name -> return AppTheme.Dark
            AppTheme.Light.name -> return AppTheme.Light
            else -> AppTheme.Default
        }
    }

    private fun loadSmsAddress(): SmsAddress {
        val smsAddressLabel = prefs.read(PrefsKeys.selectedSmsAddress) as String
        return when (smsAddressLabel) {
            SmsAddress.BNB.name -> return SmsAddress.BNB
            SmsAddress.ASB.name -> return SmsAddress.ASB
            SmsAddress.BSB.name -> return SmsAddress.BSB
            else -> SmsAddress.BNB
        }
    }

    private fun loadSelectedLang(): LanTags {
        val lang = prefs.read(PrefsKeys.lang) as String
        return when (lang) {
            LanTags.RU.tag -> return LanTags.RU
            LanTags.BE.tag -> return LanTags.BE
            LanTags.EN.tag -> return LanTags.EN
            else -> LanTags.RU
        }
    }

    private fun langSelection(context: Context, localeTag: String) {
        prefs.write(PrefsKeys.lang, localeTag)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(localeTag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeTag)
            )
        }
    }
}

sealed class SettingsEvent {
    data class ChangeTheme(val theme: AppTheme) : SettingsEvent()
    data class ChangeLang(val selectedLang: LanTags, var context: Context) : SettingsEvent()
    data class SetSmsBankAddress(val smsAddress: SmsAddress) : SettingsEvent()
}

data class SettingsState(
    val theme: AppTheme,
    val smsAddress: SmsAddress,
    val selectedLang: LanTags
)