package com.example.smsbankinganalitics.view_models.services.SmsParsers

import android.content.Context
import com.example.smsbankinganalitics.model.ActionCategory
import com.example.smsbankinganalitics.model.SmsCommonInfo
import com.example.smsbankinganalitics.model.SmsParsedBody
import com.example.smsbankinganalitics.model.Terminal
import java.time.LocalDateTime
import javax.inject.Inject

class SmsAsbParser @Inject constructor(val context: Context) : SmsParser() {
    override fun toParsedSmsBody(
        noParsedSmsBody: Map.Entry<String, LocalDateTime>,
        makeAssociations: Boolean
    ): SmsParsedBody {
        TODO("Not yet implemented")
    }

    override fun toSmsCommonInfo(body: String): SmsCommonInfo {
        TODO("Not yet implemented")
    }

    override fun terminalParser(
        body: String,
        actionCategory: ActionCategory,
        makeAssociations: Boolean
    ): Terminal {
        TODO("Not yet implemented")
    }

    override fun parseActionCategory(body: String): ActionCategory {
        TODO("Not yet implemented")
    }

    override fun parseAmount(body: String): Double {
        TODO("Not yet implemented")
    }

    override fun parseCardMask(body: String): String {
        TODO("Not yet implemented")
    }

    override fun parseAvailableAmount(body: String): Double {
        TODO("Not yet implemented")
    }

    override fun parseDate(body: String): String? {
        TODO("Not yet implemented")
    }

    override fun parseCurrency(body: String): String {
        TODO("Not yet implemented")
    }

    override fun parseTerminalAssociations(noAssociatedName: String): String {
        TODO("Not yet implemented")
    }

}