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

    val dateWithTime: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")


    private val dateOnly: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")


    fun fromLocalDateTimeToStringDate(localDate: LocalDateTime):String {
       return localDate.format(dateOnly)
    }


    fun fromMilliToLocalDateTime(dateSentMillis: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateSentMillis), ZoneId.systemDefault())
    }


    fun fromLocalDateTimeToMilli(localDateTime: LocalDateTime): Long {
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        return instant.toEpochMilli()
    }


    fun fromMilliToDate(dateSentMillis: Long): Date {
        return Date(dateSentMillis)
    }


    fun simpleStringDateFormatFromMilli(dateSentMillis: Long):String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return fromMilliToDate(dateSentMillis).let { sdf.format(it) }
    }
}