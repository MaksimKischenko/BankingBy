package com.example.smsbankinganalitics.model

import com.example.smsbankinganalitics.R

enum class ActionCategory(val arrayOfActions: Array<String>, val resId: Int) {
    PAYMENT(arrayOf("Oplata", "Oplata v internete"), R.string.payment),
    AVAILABLE(arrayOf("Dostupno"), R.string.available),
    TRANSFER_FROM(arrayOf("Spisan perevod", "Sniatie nalichnyh"), R.string.transfer_from),
    UNKNOWN(arrayOf("unknown"), R.string.unknown),
    TRANSFER_TO(arrayOf("Zachisleno", "Zachislen perevod"), R.string.transfer_to),
}