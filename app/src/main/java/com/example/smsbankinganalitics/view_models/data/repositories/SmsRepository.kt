package com.example.smsbankinganalitics.view_models.data.repositories

import java.time.LocalDateTime
import javax.inject.Inject

class SmsRepository  @Inject constructor() {

    private var dynamicSmsMap: MutableMap<String, LocalDateTime> = mutableMapOf()

    fun getAll(): MutableMap<String, LocalDateTime> {
        return dynamicSmsMap
    }
    fun addAll(smsMap: MutableMap<String, LocalDateTime>) {
        this.dynamicSmsMap = smsMap
    }

    fun addSms(smsEntry: Pair<String, LocalDateTime>) {
        dynamicSmsMap[smsEntry.first] = smsEntry.second
    }

    fun clear() {
        dynamicSmsMap.clear()
    }
}