package com.example.smsbankinganalitics.services.SMSParser

import android.content.Context
import android.util.Log
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.AssociationTerminal
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.models.Terminal
import com.example.smsbankinganalitics.utils.AppRegex
import javax.inject.Inject

class SmsBnbParser @Inject constructor(val context: Context) : SmsParser() {
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
            "Без терминала"
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

    override fun parseDate(body: String): String {
        val dateMatch = AppRegex.dataRegex.find(body)
        return dateMatch?.groupValues?.get(1) ?: ""
    }

    override fun parseCurrency(body: String): String {
        return when {
            AppRegex.bynRegex.containsMatchIn(body) -> "BYN"
            AppRegex.usdRegex.containsMatchIn(body) -> "USD"
            AppRegex.eurRegex.containsMatchIn(body) -> "EUR"
            else -> context.getString(R.string.unknown)
        }
    }

    override fun parseTerminalAssociations(noAssociatedName: String): String {
        val association = AssociationTerminal.entries.find {array-> array.noAssociatedArray.any {it.contains(noAssociatedName, ignoreCase = true) } }
//        Log.d("MyLog", "associationNAme: $association")
        return association?.let { context.getString(it.resId) } ?: context.getString(AssociationTerminal.OTHER.resId)
    }


//    override fun parseTerminalAssociations(noAssociatedName: String): String  {
//        Log.d("MyLog", "noAssociatedName: $noAssociatedName")
//        var resultName = ""
//        AssociationTerminal.entries.forEach {
//
//
//
//            resultName = if ( it.noAssociatedArray.contains(noAssociatedName)) {
//                context.getString(it.resId)
//
//            } else {
//                context.getString(AssociationTerminal.OTHER.resId)
//            }
//        }
//
//        return  resultName
//    }
}
