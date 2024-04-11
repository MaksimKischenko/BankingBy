package com.example.smsbankinganalitics.data.repositories

import java.time.LocalDateTime
import javax.inject.Inject

class SmsRepository  @Inject constructor() {
    private var smsMap: Map<String, LocalDateTime> = mapOf()
    fun getSmsMap(): Map<String, LocalDateTime> {
        return smsMap
    }

    fun addSmsMap(smsMap: Map<String, LocalDateTime>) {
        this.smsMap = smsMap
    }
}