package com.example.smsbankinganalitics.models

data class SmsArgs(
    val address: String,
    val dateFrom:Long? = null,
    val dateTo: Long? = null

)

