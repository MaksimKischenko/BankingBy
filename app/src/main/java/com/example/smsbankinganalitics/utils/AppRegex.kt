package com.example.smsbankinganalitics.utils

object AppRegex {
    val dataRegex = Regex("""(\d{2}\.\d{2}\.\d{4} \d{2}:\d{2}:\d{2})""")
    val dataRegex2 = Regex("""(\d{4}\-\d{2}\-\d{2} \d{2}:\d{2}:\d{2})""")
    val sumRegex = Regex("""\d+\.\d{2}""")
    val actionCategoryRegex = Regex("""(\w+(?:\s+\w+)*)\s+\d+\.\d{2}""", RegexOption.IGNORE_CASE)
    val terminalNameRegex = Regex("""(\b\w+(?:\s"\w+(?:\s\w+)*)?\s\w+>\b)""")
    val bynRegex = Regex("""BYN""", RegexOption.IGNORE_CASE)
    val usdRegex = Regex("""USD""", RegexOption.IGNORE_CASE)
    val eurRegex = Regex("""EUR""", RegexOption.IGNORE_CASE)
}