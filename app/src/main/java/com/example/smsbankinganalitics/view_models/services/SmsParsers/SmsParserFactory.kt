package com.example.smsbankinganalitics.view_models.services.SmsParsers

import android.content.Context
import com.example.smsbankinganalitics.model.SmsAddress
import javax.inject.Inject

class SmsParserFactory  @Inject constructor(val context: Context) {
    fun setParserBankType(smsAddress: SmsAddress) : SmsParser {
        return when(smsAddress) {
            SmsAddress.BNB -> {
                SmsBnbParser(context)
            }
            SmsAddress.ASB -> {
                SmsAsbParser(context)
            }
            SmsAddress.BSB -> {
                SmsUnknownParser(context)
            }

            SmsAddress.UNKNOWN -> {
                SmsUnknownParser(context)
            }
        }
    }
}