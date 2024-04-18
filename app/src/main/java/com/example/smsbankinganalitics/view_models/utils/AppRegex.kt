package com.example.smsbankinganalitics.view_models.utils

object AppRegex {
    val dataRegex = Regex("""(\d{2}\.\d{2}\.\d{4} \d{2}:\d{2}:\d{2})""")
    val sumRegex = Regex("""\d+\.\d{2}""")
    val availableLineRegex= Regex("""Dostupno: (\d+\.\d+)""")
    val cardMask = Regex("Visa#\\d+")
    val availableSumRegex= Regex("[^\\d.]")
    val actionCategoryRegex = Regex("""(\w+(?:\s+\w+)*)\s+\d+\.\d{2}""", RegexOption.IGNORE_CASE)
    val bynRegex = Regex("""BYN""", RegexOption.IGNORE_CASE)
    val usdRegex = Regex("""USD""", RegexOption.IGNORE_CASE)
    val eurRegex = Regex("""EUR""", RegexOption.IGNORE_CASE)
}