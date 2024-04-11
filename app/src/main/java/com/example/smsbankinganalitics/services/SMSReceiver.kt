package com.example.smsbankinganalitics.services

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import com.example.smsbankinganalitics.models.SmsArgs
import com.example.smsbankinganalitics.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime


object SMSReceiver {
    init {
        Log.d("MyLog", "INIT SMSReceiver")
    }

    //В этом оптимизированном коде мы используем  async  для запуска асинхронной задачи для каждого адреса из  addressArray ,
    //а затем используем  awaitAll()  для дожидания завершения всех асинхронных задач.
    //Результаты каждой задачи объединяются в общий список с помощью  flatten() .
    suspend fun getAllSMSByAddress(smsArgs: SmsArgs, context: Context): Map<String, LocalDateTime> = coroutineScope {
        val deferredList = smsArgs.addressArray.map { address ->
            async(Dispatchers.IO) {
                val tempSmsMap: MutableMap<String, LocalDateTime> = mutableMapOf()
                formCursor(smsArgs, context, address)?.use { inner ->
                    val bodyIndex = inner.getColumnIndex(Telephony.Sms.BODY)
                    val dateIndex = inner.getColumnIndex(Telephony.Sms.DATE_SENT)
                    if (bodyIndex != -1 && dateIndex != -1) {
                        while (inner.moveToNext()) {
                            val body = inner.getString(bodyIndex)
                            val dateSentMillis = inner.getLong(dateIndex)
                            val dateSent = DateUtils.fromMilliToLocalDateTime(dateSentMillis)
                            tempSmsMap[body] = dateSent
                        }
                    } else {
                        Log.d("MyLog", "NOTHING")
                    }
                }
                tempSmsMap
            }
        }
        val result = deferredList.awaitAll()
        result.fold(mutableMapOf()) { acc, map ->
            acc.putAll(map)
            acc
        }
    }
    private fun formCursor(smsArgs: SmsArgs, context: Context, item: String): Cursor? {
        val uriSms = Uri.parse("content://sms/inbox")
        val selection =
            "address=?" + if (smsArgs.dateFrom != null) "AND date BETWEEN ${smsArgs.dateFrom} AND ${smsArgs.dateTo}" else ""
        Log.d("MyLog", "selection: $selection")
        val projection = arrayOf(Telephony.Sms.BODY, Telephony.Sms.DATE_SENT)
        val sortOrder = "date_sent DESC"
        return context.contentResolver.query(
            uriSms,
            projection,
            selection,
            arrayOf(item),
            sortOrder
        )
    }
}