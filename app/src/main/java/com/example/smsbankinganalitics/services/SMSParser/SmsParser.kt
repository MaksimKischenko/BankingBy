package com.example.smsbankinganalitics.services.SMSParser

import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.models.Terminal
import java.time.LocalDateTime

abstract class SmsParser {
    abstract fun toParsedSMSBody(noParsedSmsBody: String, makeAssociations: Boolean = false) : SmsParsedBody
    protected abstract fun terminalParser(body: String, actionCategory: ActionCategory, makeAssociations: Boolean): Terminal
    protected abstract fun parseActionCategory(body: String): ActionCategory
    protected abstract fun parseAmount(body: String): Double
    protected abstract fun parseDate(body: String): String?
    protected abstract fun parseCurrency(body: String): String
    protected abstract fun parseTerminalAssociations(noAssociatedName: String): String
}