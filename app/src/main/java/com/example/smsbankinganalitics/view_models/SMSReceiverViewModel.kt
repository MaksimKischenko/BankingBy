package com.example.smsbankinganalitics.view_models

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smsbankinganalitics.data.PreferencesManager
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsArgs
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.services.SMSParser.SmsBnbParser
import com.example.smsbankinganalitics.services.SMSParser.SmsParser
import com.example.smsbankinganalitics.services.SMSReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


//class SMSReceiverViewModelFactory @Inject constructor(
//    private val smsParser: SmsBnbParser
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SMSReceiverViewModel::class.java)) {
//            return SMSReceiverViewModel(smsParser) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}


@HiltViewModel
class SMSReceiverViewModel @Inject constructor(
    private val smsParser: SmsBnbParser
) : ViewModel() {
    var stateApp by mutableStateOf(SMSReceiverState())

    init {
        Log.d("MyLog", "INIT SMSReceiverViewModel")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: SMSReceiverEvent) {
        val startTime = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.Default) {
            try {
                when (event) {
                    is SMSReceiverEvent.SMSReceiverByArgs -> {
                        val smsReceiver = SMSReceiver
                        stateApp = stateApp.copy(isLoading = true)
                        val noParsedSmsList =
                            smsReceiver.getAllSMSByAddress(event.smsArgs, event.context)
                        val parsedSmsList = filterAndSort(noParsedSmsList)
                        Log.d("MyLog", "SMSReceiverViewModel: $parsedSmsList")
                        stateApp = stateApp.copy(isLoading = false, smsReceivedList = parsedSmsList)
                    }
                }
            } catch (e: Exception) {
                stateApp = stateApp.copy(isLoading = false, errorMessage = e.message)
            }
            val endTime = System.currentTimeMillis() // Время после выполнения метода
            val executionTime = endTime - startTime // Вычисление времени выполнения метода
            Log.d("MyLog", "Method execution time: $executionTime ms")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
     private fun filterAndSort(noParsedSmsList: List<String>) : List<SmsParsedBody> {
        return noParsedSmsList.map { sms -> smsParser.toParsedSMSBody(sms) }
            .filter { item -> item.actionCategory != ActionCategory.UNKNOWN }
            .sortedWith(compareByDescending { it.paymentDate })
    }

    fun showErrorSnackBar(
        scope: CoroutineScope, snackbarHostState: SnackbarHostState
    ) {
        if (stateApp.errorMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = stateApp.errorMessage ?: "",
                    duration = SnackbarDuration.Short,
                )
            }
        }
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