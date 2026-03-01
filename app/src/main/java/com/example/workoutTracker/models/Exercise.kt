package com.example.workouttracker.models

data class Exercise(
    val id: Int,
    val name: String,
    val sets: List<WorkoutSet>
)
