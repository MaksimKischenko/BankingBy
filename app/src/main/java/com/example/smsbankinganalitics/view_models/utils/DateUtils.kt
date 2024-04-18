package com.example.smsbankinganalitics.view_models.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    val dateWithTime: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateOnly: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")


    @RequiresApi(Build.VERSION_CODES.O)
    fun fromLocalDateTimeToStringDate(localDate: LocalDateTime):String {
       return localDate.format(dateOnly)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fromMilliToLocalDateTime(dateSentMillis: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateSentMillis), ZoneId.systemDefault())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fromLocalDateTimeToMilli(localDateTime: LocalDateTime): Long {
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        return instant.toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fromMilliToDate(dateSentMillis: Long): Date {
        return Date(dateSentMillis)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun simpleStringDateFormatFromMilli(dateSentMillis: Long):String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return fromMilliToDate(dateSentMillis).let { sdf.format(it) }
    }
}