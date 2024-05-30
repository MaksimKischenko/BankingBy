package com.production.smsbankinganalitics.model

import com.production.smsbankinganalitics.R


enum class SmsAddress(val labelArray: Array<String>, val resId:Int) {
    NO(arrayOf("NO"), R.drawable.unknown),
    BNB(arrayOf("BNB-BANK","BNB-Bank"), R.drawable.bnb_logo),
    ASB(arrayOf("ASB.BY", "Mbank"), R.drawable.asb_logo),
    BSB(arrayOf("BSB-Bank"), R.drawable.bsb_logo),
}