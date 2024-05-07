package com.production.smsbankinganalitics.view_models.services.SmsParsers

import android.content.Context
import com.production.smsbankinganalitics.R
import com.production.smsbankinganalitics.model.ActionCategory
import com.production.smsbankinganalitics.model.SmsCommonInfo
import com.production.smsbankinganalitics.model.SmsParsedBody
import com.production.smsbankinganalitics.model.Terminal
import com.production.smsbankinganalitics.view_models.utils.Localization
import java.time.LocalDateTime
import javax.inject.Inject


class SmsUnknownParser @Inject constructor(val context: Context) : SmsParser() {
    override fun toParsedSmsBody(
        noParsedSmsBody: Map.Entry<String, LocalDateTime>,
        makeAssociations: Boolean
    ): SmsParsedBody {
        return SmsParsedBody(
            paymentSum = 0.0,
            paymentCurrency = Localization.withContext(context, R.string.unknown),
            paymentDate = LocalDateTime.now(),
            actionCategory = ActionCategory.UNKNOWN,
            terminal = Terminal(
                isAssociated = false,
                Localization.withContext(context, R.string.unknown),
                Localization.withContext(context, R.string.unknown)
            )
        )
    }

    override fun toSmsCommonInfo(body: String): SmsCommonInfo {
        return SmsCommonInfo(
            Localization.withContext(context, R.string.unknown),
            resId = 0,
            availableAmount = 0.0,
        )
    }

    override fun terminalParser(
        body: String,
        actionCategory: ActionCategory,
        makeAssociations: Boolean
    ): Terminal {
       return Terminal(
           isAssociated = false,
           Localization.withContext(context, R.string.unknown),
           Localization.withContext(context, R.string.unknown)
       )
    }

    override fun parseActionCategory(body: String): ActionCategory {
        return ActionCategory.UNKNOWN
    }

    override fun parseAmount(body: String): Double {
       return 0.0
    }

    override fun parseCardMask(body: String): String {
        return  Localization.withContext(context, R.string.unknown)
    }

    override fun parseAvailableAmount(body: String): Double {
        return 0.0
    }

    override fun parseCurrency(body: String): String {
        return Localization.withContext(context, R.string.unknown)
    }

    override fun parseTerminalAssociations(noAssociatedName: String): String {
        return  Localization.withContext(context, R.string.unknown)
    }
}