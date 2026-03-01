package com.example.workouttracker.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    fun getFormattedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM. EEEE", Locale.ENGLISH)
        return date.format(formatter).lowercase(Locale.ENGLISH)
    }

    fun getLastFourDays(currentDate: LocalDate): List<LocalDate> {
        return listOf(
            currentDate.minusDays(4),
            currentDate.minusDays(3),
            currentDate.minusDays(2),
            currentDate.minusDays(1)
        )
    }
}