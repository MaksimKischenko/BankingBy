package com.example.smsbankinganalitics.services.SMSParser

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.AssociationTerminal
import com.example.smsbankinganalitics.models.Currencies
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.models.Terminal
import com.example.smsbankinganalitics.utils.AppRegex
import com.example.smsbankinganalitics.utils.DateFormatters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SmsBnbParser @Inject constructor(val context: Context) : SmsParser() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun toParsedSMSBody(noParsedSmsBody: String, makeAssociations: Boolean): SmsParsedBody {
        val actionCategory = parseActionCategory(noParsedSmsBody)
        return SmsParsedBody(
            paymentCurrency = parseCurrency(noParsedSmsBody),
            paymentSum = parseAmount(noParsedSmsBody),
            paymentDate = parseDate(noParsedSmsBody),
            actionCategory = actionCategory,
            terminal = terminalParser(noParsedSmsBody, actionCategory, true)

        )
    }

    override fun terminalParser(body: String, actionCategory: ActionCategory, makeAssociations: Boolean): Terminal {
        val noAssociatedName = if (actionCategory == ActionCategory.PAYMENT) {
            body.split(';')[1].split('>')[0]
        } else {
            context.getString(R.string.unknown)
        }

        val associatedName = if (makeAssociations) parseTerminalAssociations(noAssociatedName) else null
        return Terminal(associatedName = associatedName?:"", isAssociated = makeAssociations, noAssociatedName = noAssociatedName)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseDate(body: String): LocalDateTime? {
        val dateMatch = AppRegex.dataRegex.find(body)
        val stringDate = dateMatch?.groupValues?.get(1) ?: ""
        val dateFormatter = DateFormatters.dateWithTime
        return if(stringDate.isNotEmpty())
            LocalDateTime.parse(stringDate, dateFormatter)
        else null
    }

    override fun parseCurrency(body: String): String {
        return when {
            AppRegex.bynRegex.containsMatchIn(body) -> Currencies.BYN.name
            AppRegex.usdRegex.containsMatchIn(body) -> Currencies.USD.name
            AppRegex.eurRegex.containsMatchIn(body) -> Currencies.EUR.name
            else -> context.getString(R.string.unknown)
        }
    }

    override fun parseTerminalAssociations(noAssociatedName: String): String {
        for (category in AssociationTerminal.entries) {
            for (associatedWord in category.noAssociatedArray) {
                if (noAssociatedName.lowercase().contains(associatedWord.lowercase())) {
                    return context.getString(category.resId)
                }
            }
        }
        return  context.getString(AssociationTerminal.OTHER.resId)
    }
}
