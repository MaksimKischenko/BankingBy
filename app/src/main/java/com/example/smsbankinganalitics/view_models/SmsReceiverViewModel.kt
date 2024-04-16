package com.example.smsbankinganalitics.view_models

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.data.repositories.SmsRepository
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsArgs
import com.example.smsbankinganalitics.models.SmsCommonInfo
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.services.SMSParser.SmsBnbParser
import com.example.smsbankinganalitics.services.SMSReceiver
import com.example.smsbankinganalitics.services.SmsBroadcastReceiver
import com.example.smsbankinganalitics.utils.DateUtils
import com.example.smsbankinganalitics.utils.Localization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@HiltViewModel
class SmsReceiverViewModel @Inject constructor(
    private val smsParser: SmsBnbParser,
    private val smsRepository: SmsRepository,
    smsBroadcastReceiver: SmsBroadcastReceiver
) : ViewModel() {
    var state by mutableStateOf(SmsReceiverState())

    init {
        smsBroadcastReceiver.setSmsReceiverViewModel(this)
        smsBroadcastReceiver.registerIntentFilters()
    }

    fun onEvent(event: SmsReceiverEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            val executionTime = measureTimeMillis {

                val smsReceiver = SMSReceiver

                try {
                    when (event) {
                        is SmsReceiverEvent.ByArgs -> {
                            if ((event.smsArgs.dateFrom ?: 0) < System.currentTimeMillis()) {
                                state = state.copy(isLoading = true)
                                val noParsedSmsMap = smsReceiver.getAllSMSByAddress(event.smsArgs, event.context)
                                var smsCommonInfo = smsParser.toSmsCommonInfo(noParsedSmsMap.keys.first())
                                smsRepository.addAll(noParsedSmsMap.toMutableMap())
                                val sortedNoParsedSmsMap = noParsedSmsMap.toList()
                                    .sortedByDescending { it.second }
                                    .toMap()
                                val parsedSmsBodies = filterAndParse(sortedNoParsedSmsMap)
                                smsCommonInfo = smsCommonInfo.copy(
                                    dateFrom = parsedSmsBodies.lastOrNull()?.paymentDate?.let {
                                        DateUtils.fromLocalDateTimeToStringDate(
                                            it
                                        )
                                    }
                                )
                                state = state.copy(
                                    isLoading = false,
                                    smsReceivedList = parsedSmsBodies.toMutableList(),
                                    smsCommonInfo = smsCommonInfo
                                )
//                                showInfoToast(event.context)
                            }
                        }

                        is SmsReceiverEvent.ByBroadcast -> {
                            state = state.copy(isLoading = true)
                            val smsEntry: Pair<String, LocalDateTime> = event.messageBody to LocalDateTime.now()
                            smsRepository.addSms(smsEntry)
                            val smsMap: Map<String, LocalDateTime> = mapOf(smsEntry.first to smsEntry.second)
                            val parsedSmsBodies = filterAndParse(smsMap).first()
                            val smsCommonInfo = smsParser.toSmsCommonInfo(smsEntry.first)
                            state.smsReceivedList?.add(0, parsedSmsBodies)
                            state = state.copy(
                                isLoading = false,
                                smsReceivedList = state.smsReceivedList,
                                smsCommonInfo = smsCommonInfo
                            )

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
                Localization.withContext(
                    context,
                    resId = R.string.count
                ) + " " + state.smsReceivedList?.size,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun filterAndParse(noParsedSmsMap: Map<String, LocalDateTime>): List<SmsParsedBody> {
        return noParsedSmsMap.map { smsEntry -> smsParser.toParsedSmsBody(smsEntry) }
            .filter { item -> item.actionCategory != ActionCategory.UNKNOWN }
    }
}

sealed class SmsReceiverEvent {
    data class ByArgs(val smsArgs: SmsArgs, val context: Context) : SmsReceiverEvent()
    data class ByBroadcast(val messageBody: String) : SmsReceiverEvent()
}


data class SmsReceiverState(
    val isLoading: Boolean = false,
    var smsReceivedList: MutableList<SmsParsedBody>? = mutableListOf(),
    var smsCommonInfo: SmsCommonInfo? = null,
    var errorMessage: String? = null
)