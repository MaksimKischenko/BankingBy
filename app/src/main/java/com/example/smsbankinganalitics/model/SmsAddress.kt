package com.example.smsbankinganalitics.model


enum class SmsAddress(val labelArray: Array<String>) {
    BNB_BANK(arrayOf("BNB-BANK","BNB-Bank")),
    BSB_BANK(arrayOf("")),
    ASB_BANK(arrayOf("ASB.BY"))
}