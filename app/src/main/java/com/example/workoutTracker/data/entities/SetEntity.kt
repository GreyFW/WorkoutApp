package com.example.workouttracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class SetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val exerciseId: Long,
    val weight: String,
    val reps: String
)