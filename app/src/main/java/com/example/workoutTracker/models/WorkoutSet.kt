package com.example.workouttracker.models

data class WorkoutSet(
    val id: Int,
    val weight: String,
    val reps: String,
    var isCompleted: Boolean = false
)
