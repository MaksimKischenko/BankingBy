package com.production.smsbankinganalitics.view_models.services.SmsParsers

import com.production.smsbankinganalitics.model.ActionCategory
import com.production.smsbankinganalitics.model.SmsCommonInfo
import com.production.smsbankinganalitics.model.SmsParsedBody
import com.production.smsbankinganalitics.model.Terminal
import java.time.LocalDateTime

abstract class SmsParser {
    abstract fun toParsedSmsBody(noParsedSmsBody: Map.Entry<String, LocalDateTime>, makeAssociations: Boolean = false) : SmsParsedBody
    abstract fun toSmsCommonInfo(body: String) : SmsCommonInfo
    protected abstract fun terminalParser(body: String, actionCategory: ActionCategory, makeAssociations: Boolean): Terminal
    protected abstract fun parseActionCategory(body: String): ActionCategory
    protected abstract fun parseAmount(body: String): Double
    protected abstract fun parseCardMask(body: String): String
    protected abstract fun parseAvailableAmount(body: String): Double
//    protected abstract fun parseDate(body: String): String?
    protected abstract fun parseCurrency(body: String): String
    protected abstract fun parseTerminalAssociations(noAssociatedName: String): String
}