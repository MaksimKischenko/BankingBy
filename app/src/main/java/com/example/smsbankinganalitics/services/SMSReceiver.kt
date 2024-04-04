package com.example.smsbankinganalitics.services

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import com.example.smsbankinganalitics.models.SmsArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


object SMSReceiver {

    init {
        Log.d("MyLog", "INIT SMSReceiver")
    }

    suspend fun getAllSMSByAddress(smsArgs: SmsArgs, context: Context): List<String> = coroutineScope{
        val smsList = mutableListOf<String>()
        launch(Dispatchers.IO) {
            formCursor(smsArgs, context)?.use { item ->
                val bodyIndex = item.getColumnIndex(Telephony.Sms.BODY)
                if (bodyIndex != -1) {
                    while (item.moveToNext()) {
                        val body = item.getString(bodyIndex)
                        smsList.add(body)
                    }
                } else {
                    Log.d("MyLog","NOTHING")
                }
            }
        }
        return@coroutineScope smsList
    }

    private fun formCursor(smsArgs: SmsArgs, context: Context): Cursor? {
        val uriSms = Uri.parse("content://sms/inbox")
        val selection = "address=? OR address=?" + if (smsArgs.dateFrom != null) " AND date BETWEEN ${smsArgs.dateFrom} AND ${smsArgs.dateTo}" else ""
        val projection = arrayOf("address, body", "date_sent")
        return context.contentResolver.query(
            uriSms,
            projection,
            selection,
            arrayOf(smsArgs.address),
            "date DESC"
        )
    }
}