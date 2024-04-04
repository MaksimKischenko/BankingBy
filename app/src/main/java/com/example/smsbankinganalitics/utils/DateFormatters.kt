package com.example.smsbankinganalitics.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

object DateFormatters {
    @RequiresApi(Build.VERSION_CODES.O)
    val dateWithTime: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

    @RequiresApi(Build.VERSION_CODES.O)
    val dateOnly: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
}