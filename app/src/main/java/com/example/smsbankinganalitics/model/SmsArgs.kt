package com.example.smsbankinganalitics.model

data class SmsArgs(
    val smsAddress: SmsAddress,
    val dateFrom:Long? = null,
    val dateTo: Long? = null

)

