package com.example.smsbankinganalitics.view_models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.data.repositories.SmsRepository
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsArgs
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.services.SMSParser.SmsBnbParser
import com.example.smsbankinganalitics.services.SMSReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@HiltViewModel
class SmsReceiverViewModel @Inject constructor(
    private val smsParser: SmsBnbParser,
    private val smsRepository: SmsRepository,
) : ViewModel() {
    var state by mutableStateOf(SMSReceiverState())

    init {
        Log.d("MyLog", "INIT SMSReceiverViewModel")
    }

    fun onEvent(event: SMSReceiverEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            val executionTime = measureTimeMillis {
                try {
                    when (event) {
                        is SMSReceiverEvent.ByArgs -> {
                            if ((event.smsArgs.dateFrom ?: 0) < System.currentTimeMillis()) {
                                val smsReceiver = SMSReceiver
                                state = state.copy(isLoading = true)
                                val noParsedSmsMap =
                                    smsReceiver.getAllSMSByAddress(event.smsArgs, event.context)
                                smsRepository.addSmsMap(noParsedSmsMap)
                                val sortedNoParsedSmsMapSmsMap = noParsedSmsMap.toList()
                                    .sortedByDescending { it.second }
                                    .toMap()
                                val parsedSmsBodies = filterAndParse(sortedNoParsedSmsMapSmsMap)
                                state = state.copy(isLoading = false, smsReceivedList = parsedSmsBodies)
                                showInfoToast(event.context)
                            }
                        }
                    }
                } catch (e: Exception) {
                    state = state.copy(isLoading = false, errorMessage = e.message)
                }
            }
            Log.d("MyLog", "TIME: $executionTime")

        }
    }
    private fun showInfoToast(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(
                context,
                context.getString(R.string.count) + " " + state.smsReceivedList?.size,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun filterAndParse(noParsedSmsMap: Map<String, LocalDateTime>): List<SmsParsedBody> {
        return noParsedSmsMap.map { smsEntry -> smsParser.toParsedSMSBody(smsEntry) }
            .filter { item -> item.actionCategory != ActionCategory.UNKNOWN }
    }
}

sealed class SMSReceiverEvent {
    data class ByArgs(val smsArgs: SmsArgs, val context: Context) : SMSReceiverEvent()
}


data class SMSReceiverState(
    val isLoading: Boolean = false,
    var smsReceivedList: List<SmsParsedBody>? = emptyList(),
    var errorMessage: String? = null
)