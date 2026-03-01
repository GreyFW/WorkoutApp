package com.example.workouttracker.data.prefs

import android.content.Context

class StreakManager(context: Context) {
    private val prefs = context.getSharedPreferences("workout_prefs", Context.MODE_PRIVATE)

    fun getStreak(): Int {
        return prefs.getInt("current_streak", 0)
    }

    fun incrementStreak() {
        val current = getStreak()
        prefs.edit().putInt("current_streak", current + 1).apply()
    }
}