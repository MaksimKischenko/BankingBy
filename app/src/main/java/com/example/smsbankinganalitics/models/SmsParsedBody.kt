package com.example.smsbankinganalitics.models

import java.time.LocalDateTime

data class SmsParsedBody(
    val paymentSum: Double,
    val paymentCurrency: String,
    val paymentDate: LocalDateTime,
    val terminal: Terminal,
    val actionCategory :ActionCategory
)
