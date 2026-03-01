package com.example.workouttracker.data.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insertWorkout(workout: WorkoutEntity): Long

    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    @Insert
    suspend fun insertSet(set: SetEntity)

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM workouts WHERE dateString = :date LIMIT 1")
    suspend fun getWorkoutByDate(date: String): WorkoutEntity?

    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    suspend fun getExercisesForWorkout(workoutId: Long): List<ExerciseEntity>

    @Query("SELECT * FROM sets WHERE exerciseId = :exerciseId")
    suspend fun getSetsForExercise(exerciseId: Long): List<SetEntity>

    @Query("SELECT * FROM notes WHERE workoutId = :workoutId")
    suspend fun getNotesForWorkout(workoutId: Long): List<NoteEntity>
}