package com.example.smsbankinganalitics.services

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import com.example.smsbankinganalitics.models.SmsArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


object SMSReceiver {
    init {
        Log.d("MyLog", "INIT SMSReceiver")
    }

    //В этом оптимизированном коде мы используем  async  для запуска асинхронной задачи для каждого адреса из  addressArray ,
    //а затем используем  awaitAll()  для дожидания завершения всех асинхронных задач.
    //Результаты каждой задачи объединяются в общий список с помощью  flatten() .
    suspend fun getAllSMSByAddress(smsArgs: SmsArgs, context: Context): List<String> = coroutineScope {
        val deferredList = smsArgs.addressArray.map { address ->
            async(Dispatchers.IO) {
                val tempSmsList: MutableList<String> = mutableListOf()
                formCursor(smsArgs, context, address)?.use { inner ->
                    val bodyIndex = inner.getColumnIndex(Telephony.Sms.BODY)
                    if (bodyIndex != -1) {
                        while (inner.moveToNext()) {
                            val body = inner.getString(bodyIndex)
                            tempSmsList.add(body)
                        }
                    } else {
                        Log.d("MyLog", "NOTHING")
                    }
                }
                tempSmsList
            }
        }
        deferredList.awaitAll().flatten()
    }
    private fun formCursor(smsArgs: SmsArgs, context: Context, item: String): Cursor? {
        val uriSms = Uri.parse("content://sms/inbox")
        val selection =
            "address=? OR address=?" + if (smsArgs.dateFrom != null) " AND date BETWEEN ${smsArgs.dateFrom} AND ${smsArgs.dateTo}" else ""
        val projection = arrayOf("address, body", "date_sent")
        return context.contentResolver.query(
            uriSms,
            projection,
            selection,
            arrayOf(item),
            "date DESC"
        )
    }
}