package com.example.workouttracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateString: String,
    val startTime: String,
    val endTime: String
)