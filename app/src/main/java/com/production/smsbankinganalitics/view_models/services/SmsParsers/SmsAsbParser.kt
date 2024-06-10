package com.production.smsbankinganalitics.view_models.services.SmsParsers

import android.content.Context
import android.util.Log
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.model.ActionCategory
import com.production.smsbankinganalitics.model.AssociationTerminal
import com.production.smsbankinganalitics.model.Currencies
import com.production.smsbankinganalitics.model.SmsCommonInfo
import com.production.smsbankinganalitics.model.SmsParsedBody
import com.production.smsbankinganalitics.model.Terminal
import com.production.smsbankinganalitics.view_models.utils.Localization
import java.time.LocalDateTime
import javax.inject.Inject

class SmsAsbParser @Inject constructor(val context: Context) : SmsParser() {
    private val bynRegex = Regex("""BYN""", RegexOption.IGNORE_CASE)
    private val usdRegex = Regex("""USD""", RegexOption.IGNORE_CASE)
    private val eurRegex = Regex("""EUR""", RegexOption.IGNORE_CASE)
    private val availableLineRegex= Regex("""OSTATOK (\d+\.\d+)""")
    private val sumRegex = Regex("""\d+(\.\d{2})?""")
    private val cardMaskRegex = Regex("KARTA #\\d+")
    private val noAssociatedNameRegex = Regex("DATA [0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2} (.*?)>[A-Z\\-]+>BY")

    override fun toParsedSmsBody(
        noParsedSmsBody: Map.Entry<String, LocalDateTime>,
        makeAssociations: Boolean
    ): SmsParsedBody {
        val actionCategory = parseActionCategory(noParsedSmsBody.key)
        return SmsParsedBody(
            paymentCurrency = parseCurrency(noParsedSmsBody.key),
            paymentSum = parseAmount(noParsedSmsBody.key),
            paymentDate = noParsedSmsBody.value,
            actionCategory = actionCategory,
            terminal = terminalParser(noParsedSmsBody.key, actionCategory, makeAssociations)
        )
    }
    override fun toSmsCommonInfo(body: String): SmsCommonInfo {
        return SmsCommonInfo(
            cardMask = "",
            availableAmount = parseAvailableAmount(body),
            resId = 0
        )
    }

    override fun terminalParser(
        body: String,
        actionCategory: ActionCategory,
        makeAssociations: Boolean
    ): Terminal {
        val noAssociatedName = if (actionCategory == ActionCategory.PAYMENT) {
            noAssociatedNameRegex.find(body)?.groupValues?.get(1)?:""
        } else {
            Localization.withContext(context, R.string.unknown)
        }

        val associatedName = when {
            actionCategory == ActionCategory.TRANSFER_FROM -> Localization.withContext(context, R.string.debiting)
            actionCategory == ActionCategory.TRANSFER_TO -> Localization.withContext(context, R.string.enrollment)
            makeAssociations -> parseTerminalAssociations(noAssociatedName)
            else -> null
        }
        return Terminal(
            associatedName = associatedName ?: "",
            isAssociated = makeAssociations,
            noAssociatedName = noAssociatedName
        )
    }


    override fun parseActionCategory(body: String): ActionCategory {
        var actionCategory = ActionCategory.UNKNOWN
        val matchCategory = body.split(" ")[0].lowercase()
        if (matchCategory.isNotEmpty()) {
            actionCategory =
                    ActionCategory.entries.find { it.arrayOfActions.contains(matchCategory) }
                    ?: ActionCategory.UNKNOWN
        }
        return actionCategory
    }


    override fun parseAmount(body: String): Double {
        val amount = sumRegex.find(body)?.value
        Log.d("MyLog", "amount $amount")
        return amount?.toDoubleOrNull() ?: 0.0
    }

    override fun parseCardMask(body: String): String {
        return cardMaskRegex.find(body)?.value?:""
    }

    override fun parseAvailableAmount(body: String): Double {
        val sum = availableLineRegex.find(body)?.groupValues?.get(1)
        return sum?.toDoubleOrNull() ?: 0.0
    }

    override fun parseCurrency(body: String): String {
        return when {
            bynRegex.containsMatchIn(body) -> Currencies.BYN.name
            usdRegex.containsMatchIn(body) -> Currencies.USD.name
            eurRegex.containsMatchIn(body) -> Currencies.EUR.name
            else -> Localization.withContext(context, R.string.unknown)
        }

    }


    override fun parseTerminalAssociations(noAssociatedName: String): String {
        for (category in AssociationTerminal.entries) {
            for (associatedWord in category.noAssociatedArray) {
                if (noAssociatedName.lowercase().contains(associatedWord.lowercase())) {
                    return Localization.withContext(context, category.resId)
                }
            }
        }
        return Localization.withContext(context, AssociationTerminal.OTHER.resId)
    }
}

