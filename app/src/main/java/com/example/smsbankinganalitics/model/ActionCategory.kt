package com.example.smsbankinganalitics.model

import com.example.smsbankinganalitics.R

//TODO FOR EVERY BANK
enum class ActionCategory(val arrayOfActions: Array<String>, val resId: Int) {
    PAYMENT(arrayOf("oplata", "oplata v internete"), R.string.payment),
    AVAILABLE(arrayOf("dostupno", "ostatok"), R.string.available),
    TRANSFER_FROM(arrayOf("spisan perevod", "sniatie nalichnyh"), R.string.transfer_from),
    UNKNOWN(arrayOf("unknown"), R.string.unknown),
    TRANSFER_TO(arrayOf("zachisleno", "zachislen perevod"), R.string.transfer_to),
}