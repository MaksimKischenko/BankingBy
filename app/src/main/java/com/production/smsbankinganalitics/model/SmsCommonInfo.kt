package com.production.smsbankinganalitics.model

data class SmsCommonInfo(
    val cardMask: String,
    val resId: Int,
    val availableAmount: Double,
    val dateFrom: String? = null,
)