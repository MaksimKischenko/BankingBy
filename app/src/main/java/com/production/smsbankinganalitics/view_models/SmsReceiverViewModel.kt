package com.production.smsbankinganalitics.view_models

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.model.ActionCategory
import com.production.smsbankinganalitics.view_models.data.repositories.SmsRepository
import com.production.smsbankinganalitics.model.SmsAddress
import com.production.smsbankinganalitics.model.SmsArgs
import com.production.smsbankinganalitics.model.SmsCommonInfo
import com.production.smsbankinganalitics.model.SmsParsedBody
import com.production.smsbankinganalitics.view_models.services.SMSReceiver
import com.production.smsbankinganalitics.view_models.services.SmsBroadcastReceiver
import com.production.smsbankinganalitics.view_models.services.SmsParsers.SmsParser
import com.production.smsbankinganalitics.view_models.services.SmsParsers.SmsParserFactory
import com.production.smsbankinganalitics.view_models.utils.DateUtils
import com.production.smsbankinganalitics.view_models.utils.Localization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@HiltViewModel
class SmsReceiverViewModel @Inject constructor(
    smsParserFactory: SmsParserFactory,
    smsBroadcastReceiver: SmsBroadcastReceiver,
    private val smsRepository: SmsRepository,
) : ViewModel() {
    var state by mutableStateOf(SmsReceiverState())
    private lateinit var smsAddress: SmsAddress
    private lateinit var smsParser: SmsParser
    private val factory = smsParserFactory


    init {
        smsBroadcastReceiver.setSmsReceiverViewModel(this)
        smsBroadcastReceiver.registerIntentFilters()
    }

    fun onEvent(event: SmsReceiverEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (event) {
                    is SmsReceiverEvent.ByArgs -> {
                        if ((event.smsArgs.dateFrom ?: 0) < System.currentTimeMillis()) {
                            state = state.copy(isLoading = true)
                            val smsReceiver = SMSReceiver
                            smsParser = factory.setParserBankType(event.smsArgs.smsAddress)
                            val noParsedSmsMap =
                                smsReceiver.getAllSMSByAddress(event.smsArgs, event.context)
                            smsAddress = event.smsArgs.smsAddress
                            if (noParsedSmsMap.isNotEmpty()) {
                                makeSmsBankingListIfExists(noParsedSmsMap, event)
                            } else {
                                notExistsAction(event)
                            }
                        }
                    }

                    is SmsReceiverEvent.ByBroadcast -> {
                        state = state.copy(isLoading = true)
                        val smsEntry: Pair<String, LocalDateTime> =
                            event.messageBody to LocalDateTime.now()
                        smsRepository.addSms(smsEntry)
                        val smsMap: Map<String, LocalDateTime> =
                            mapOf(smsEntry.first to smsEntry.second)
                        val parsedSmsBody = filterAndParse(smsMap).first()
                        var smsCommonInfo = smsParser.toSmsCommonInfo(smsEntry.first)
                        smsCommonInfo = smsCommonInfo.copy(
                            dateFrom = parsedSmsBody.paymentDate.let {
                                DateUtils.fromLocalDateTimeToStringDate(
                                    it
                                )
                            },
                            resId = smsAddress.resId

                        )
                        state.smsReceivedList?.add(0, parsedSmsBody)
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
    }


    private fun makeSmsBankingListIfExists(
        noParsedSmsMap: Map<String, LocalDateTime>,
        event: SmsReceiverEvent.ByArgs
    ) {
        val sortedNoParsedSmsMap = noParsedSmsMap.toList()
            .sortedByDescending { it.second }
            .toMap()
        val parsedSmsBodies = filterAndParse(sortedNoParsedSmsMap)
        var smsCommonInfo = smsParser.toSmsCommonInfo(noParsedSmsMap.keys.first())
        smsCommonInfo = smsCommonInfo.copy(
            resId = event.smsArgs.smsAddress.resId,
            dateFrom = parsedSmsBodies.lastOrNull()?.paymentDate?.let {
                DateUtils.fromLocalDateTimeToStringDate(
                    it
                )
            }
        )
        smsRepository.addAll(noParsedSmsMap.toMutableMap())
        state = state.copy(
            isLoading = false,
            smsReceivedList = parsedSmsBodies.toMutableList(),
            smsCommonInfo = smsCommonInfo
        )
        showInfoToast(event.context)
    }

    private fun notExistsAction(event: SmsReceiverEvent.ByArgs) {
        val smsCommonInfo = SmsCommonInfo(
            cardMask = Localization.withContext(event.context, R.string.unknown),
            availableAmount = 0.0,
            resId = event.smsArgs.smsAddress.resId,
        )
        state = state.copy(
            isLoading = false,
            smsReceivedList = mutableListOf(),
            smsCommonInfo = smsCommonInfo
        )
        smsRepository.clear()
        showInfoToast(event.context)
    }


    private fun filterAndParse(noParsedSmsMap: Map<String, LocalDateTime>): List<SmsParsedBody> {
        return noParsedSmsMap.map { smsEntry ->
            smsParser.toParsedSmsBody(smsEntry)
        }.filter { item -> item.actionCategory != ActionCategory.UNKNOWN }
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