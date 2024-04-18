package com.example.smsbankinganalitics.view_models.services.SMSParser

import android.content.Context
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.model.ActionCategory
import com.example.smsbankinganalitics.model.AssociationTerminal
import com.example.smsbankinganalitics.model.Currencies
import com.example.smsbankinganalitics.model.SmsCommonInfo
import com.example.smsbankinganalitics.model.SmsParsedBody
import com.example.smsbankinganalitics.model.Terminal
import com.example.smsbankinganalitics.view_models.utils.AppRegex
import com.example.smsbankinganalitics.view_models.utils.Localization
import java.time.LocalDateTime
import javax.inject.Inject

class SmsBnbParser @Inject constructor(val context: Context) : SmsParser() {
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
            cardMask = parseCardMask(body),
            availableAmount = parseAvailableAmount(body),
        )
    }

    override fun terminalParser(
        body: String,
        actionCategory: ActionCategory,
        makeAssociations: Boolean
    ): Terminal {
        val noAssociatedName = if (actionCategory == ActionCategory.PAYMENT) {
            body.split(';')[1].split('>')[0]
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
        val matchCategory = AppRegex.actionCategoryRegex.find(body)?.groupValues?.get(1) ?: ""
        if (matchCategory.isNotEmpty()) {
            actionCategory =
                ActionCategory.entries.find { it.arrayOfActions.contains(matchCategory) }
                    ?: ActionCategory.UNKNOWN
        }
        return actionCategory
    }

    override fun parseAmount(body: String): Double {
        val amount = AppRegex.sumRegex.find(body)?.value
        return amount?.toDoubleOrNull() ?: 0.0
    }

    override fun parseCardMask(body: String): String {
        return AppRegex.cardMask.find(body)?.value?:""
    }

    override fun parseAvailableAmount(body: String): Double {
        var sum = 0.0
        val amountLine = AppRegex.availableLineRegex.find(body)?.value
        if (amountLine != null) {
            sum =  AppRegex.availableSumRegex.replace(amountLine, "").toDoubleOrNull() ?: 0.0
        }
        return sum
    }

    override fun parseDate(body: String): String? {
        val dateMatch = AppRegex.dataRegex.find(body)
        return if (dateMatch != null) {
            dateMatch.groupValues[1]
        } else {
            null
        }
    }

    override fun parseCurrency(body: String): String {
        return when {
            AppRegex.bynRegex.containsMatchIn(body) -> Currencies.BYN.name
            AppRegex.usdRegex.containsMatchIn(body) -> Currencies.USD.name
            AppRegex.eurRegex.containsMatchIn(body) -> Currencies.EUR.name
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
