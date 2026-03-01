package com.example.workouttracker.data.prefs

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class WorkoutPrefs(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("workout_prefs", Context.MODE_PRIVATE)

    val streak: Int get() = prefs.getInt("streak", 0)

    val totalWorkouts: Int get() = prefs.getInt("total_workouts", 0)

    val level: Int get() = totalWorkouts / 10

    val maxLevel: Int get() = ((level / 10) + 1) * 10

    fun getTrainedDates(): Set<String> {
        return prefs.getStringSet("trained_dates", emptySet()) ?: emptySet()
    }

    fun saveWorkout(date: LocalDate) {
        val dateStr = date.toString()
        val trainedDates = getTrainedDates().toMutableSet()

        if (trainedDates.contains(dateStr)) return

        trainedDates.add(dateStr)

        val lastDateStr = prefs.getString("last_workout_date", null)
        var newStreak = streak

        if (lastDateStr != null) {
            val lastDate = LocalDate.parse(lastDateStr)
            val daysBetween = ChronoUnit.DAYS.between(lastDate, date)

            when {
                daysBetween >= 4 -> newStreak = 1
                daysBetween > 0 -> newStreak += 1
            }
        } else {
            newStreak = 1
        }

        val shouldUpdateLastDate = lastDateStr == null || LocalDate.parse(lastDateStr).isBefore(date)

        val editor = prefs.edit()
            .putInt("streak", newStreak)
            .putInt("total_workouts", totalWorkouts + 1)
            .putStringSet("trained_dates", trainedDates)

        if (shouldUpdateLastDate) {
            editor.putString("last_workout_date", dateStr)
        }

        editor.apply()
    }
}