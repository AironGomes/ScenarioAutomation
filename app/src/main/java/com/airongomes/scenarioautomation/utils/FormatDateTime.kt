package com.airongomes.scenarioautomation.utils

import android.content.Context
import android.os.Build
import android.text.format.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

/**
 * Esta função é responsável por retornar a data e o horário em formato Short
 */
fun formatDateTimeStyle(cal: Calendar, context: Context): String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
        val dateFormat = LocalDateTime.of(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE))
        dateFormat.format(dateTimeFormatter)
    } else{
        val dateFormatter = DateFormat.getDateFormat(context)
        val timeFormatter = DateFormat.getTimeFormat(context)
        "${dateFormatter.format(cal.time)} ${timeFormatter.format(cal.time)}"
    }
}