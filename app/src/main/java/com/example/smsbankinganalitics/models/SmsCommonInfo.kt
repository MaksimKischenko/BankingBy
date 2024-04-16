package com.example.smsbankinganalitics.models

import java.time.LocalDateTime

data class SmsCommonInfo(
    val cardMask: String,
    val availableAmount: Double,
    val dateFrom: String? = null,
)