package com.example.smsbankinganalitics.view_models

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsArgs
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.services.SMSParser.SmsBnbParser
import com.example.smsbankinganalitics.services.SMSReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SmsReceiverViewModel @Inject constructor(
    private val smsParser: SmsBnbParser
) : ViewModel() {
    var stateApp by mutableStateOf(SMSReceiverState())

    init {
        Log.d("MyLog", "INIT SMSReceiverViewModel")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: SMSReceiverEvent) {

        viewModelScope.launch(Dispatchers.Default) {
            try {
                when (event) {
                    is SMSReceiverEvent.SMSReceiverByArgs -> {
                        val smsReceiver = SMSReceiver
                        stateApp = stateApp.copy(isLoading = true)
                        val noParsedSmsMap =
                            smsReceiver.getAllSMSByAddress(event.smsArgs, event.context)
                        val sortedNoParsedSmsMapSmsMap = noParsedSmsMap.toList()
                            .sortedByDescending { it.second }
                            .toMap()
                        val parsedSmsList = filterAndParse(sortedNoParsedSmsMapSmsMap.keys.toList())
//                        Log.d("MyLog", "SMSReceiverViewModel: $parsedSmsList")
                        stateApp = stateApp.copy(isLoading = false, smsReceivedList = parsedSmsList)
                    }
                }
            } catch (e: Exception) {
                stateApp = stateApp.copy(isLoading = false, errorMessage = e.message)
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterAndParse(noParsedSmsList: List<String>): List<SmsParsedBody> {
        return noParsedSmsList.map { sms -> smsParser.toParsedSMSBody(sms) }
            .filter { item -> item.actionCategory != ActionCategory.UNKNOWN }
    }
}

sealed class SMSReceiverEvent {
    data class SMSReceiverByArgs(val smsArgs: SmsArgs, val context: Context) : SMSReceiverEvent()
}


data class SMSReceiverState(
    val isLoading: Boolean = false,
    var smsReceivedList: List<SmsParsedBody>? = emptyList(),
    var errorMessage: String? = null
)