package com.example.smsbankinganalitics.model

data class SmsCommonInfo(
    val cardMask: String,
    val availableAmount: Double,
    val dateFrom: String? = null,
)