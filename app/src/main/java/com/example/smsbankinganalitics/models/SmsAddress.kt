package com.example.smsbankinganalitics.models


enum class SmsAddress(val labelArray: Array<String>) {
    BNB(arrayOf("BNB-BANK","BNB-Bank")),
    BSB(arrayOf("")),
    ASB(arrayOf("ASB.BY"))
}