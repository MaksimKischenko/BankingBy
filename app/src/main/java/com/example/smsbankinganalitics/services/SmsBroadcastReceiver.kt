package com.example.smsbankinganalitics.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Telephony
import androidx.annotation.RequiresApi
import com.example.smsbankinganalitics.models.SmsAddress
import com.example.smsbankinganalitics.view_models.SMSReceiverEvent
import com.example.smsbankinganalitics.view_models.SmsReceiverViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class SmsBroadcastReceiver @Inject constructor(val context: Context) {
    private var smsReceiverViewModel: SmsReceiverViewModel? = null

    fun setSmsReceiverViewModel(viewModel: SmsReceiverViewModel) {
        smsReceiverViewModel = viewModel
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun registerIntentFilters() {
        context.registerReceiver(
            smsReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
    }

    private val smsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
                val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                for (smsMessage in smsMessages) {
                    val sender = smsMessage.displayOriginatingAddress
                    checkSmsAddress(sender) {
                        val messageBody = smsMessage.messageBody
                        smsReceiverViewModel?.onEvent(SMSReceiverEvent.ByBroadcast(messageBody))
                    }
                }
            }
        }
    }

    private fun checkSmsAddress(address: String, onAction: () -> Unit) {
        for (enumValue in SmsAddress.entries) {
            if (enumValue.labelArray.any { it.equals(address, ignoreCase = true) }) {
                onAction.invoke()
                return
            }
        }
    }
}