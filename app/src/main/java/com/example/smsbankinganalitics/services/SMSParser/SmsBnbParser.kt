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
import com.example.smsbankinganalitics.utils.DateUtils
import java.time.LocalDateTime
import javax.inject.Inject

class SmsBnbParser @Inject constructor(val context: Context) : SmsParser() {
    override fun toParsedSMSBody(noParsedSmsBody: Map.Entry<String, LocalDateTime>, makeAssociations: Boolean): SmsParsedBody {
        val actionCategory = parseActionCategory(noParsedSmsBody.key)
        return SmsParsedBody(
            paymentCurrency =  parseCurrency(noParsedSmsBody.key),
            paymentSum = parseAmount(noParsedSmsBody.key),
            paymentDate = noParsedSmsBody.value, //format(DateUtils.dateOnly),
            actionCategory =  actionCategory,
            terminal = terminalParser(noParsedSmsBody.key, actionCategory, false)

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

    override fun parseDate(body: String): String? {
        val dateMatch = AppRegex.dataRegex.find(body)
        return if(dateMatch !=null) {
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
            else -> context.getString(R.string.unknown)
        }
    }

    override fun parseTerminalAssociations(noAssociatedName: String): String {
        for (category in AssociationTerminal.entries) {
            for (associatedWord in category.noAssociatedArray) {
                if (noAssociatedName.lowercase().contains(associatedWord.lowercase())) {
                    return  context.getString(category.resId)
                }
            }
        }
        return context.getString(AssociationTerminal.OTHER.resId)
    }
}
