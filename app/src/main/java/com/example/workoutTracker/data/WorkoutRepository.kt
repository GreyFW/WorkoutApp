package com.example.workouttracker.data

import com.example.workouttracker.data.entities.ExerciseEntity
import com.example.workouttracker.data.entities.NoteEntity
import com.example.workouttracker.data.entities.SetEntity
import com.example.workouttracker.data.entities.WorkoutDao
import com.example.workouttracker.data.entities.WorkoutEntity
import com.example.workouttracker.models.Exercise
import com.example.workouttracker.models.Note
import com.example.workouttracker.models.WorkoutSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class WorkoutRepository(private val dao: WorkoutDao) {

    // -------------------------------------------------------------------------
    // Workout

    private suspend fun getOrCreateWorkoutId(date: LocalDate): Long =
        withContext(Dispatchers.IO) {
            dao.getWorkoutByDate(date.toString())?.id
                ?: dao.insertWorkout(
                    WorkoutEntity(
                        dateString = date.toString(),
                        startTime = "",
                        endTime = ""
                    )
                )
        }

    // -------------------------------------------------------------------------
    // Exercises

    suspend fun getExercisesForDate(date: LocalDate): List<Exercise> =
        withContext(Dispatchers.IO) {
            val workout = dao.getWorkoutByDate(date.toString())
                ?: return@withContext emptyList()

            dao.getExercisesForWorkout(workout.id).map { entity ->
                val sets = dao.getSetsForExercise(entity.id).mapIndexed { index, setEntity ->
                    WorkoutSet(
                        id = index,
                        weight = setEntity.weight,
                        reps = setEntity.reps
                    )
                }
                Exercise(
                    id = entity.id.toInt(),
                    name = entity.name,
                    sets = sets
                )
            }
        }

    @androidx.room.Transaction
    suspend fun saveExercises(date: LocalDate, exercises: List<Exercise>) =
        withContext(Dispatchers.IO) {
            val workoutId = getOrCreateWorkoutId(date)

            dao.deleteSetsForWorkout(workoutId)
            dao.deleteExercisesForWorkout(workoutId)

            exercises.forEach { exercise ->
                if (exercise.name.isBlank()) return@forEach

                val exerciseId = dao.insertExercise(
                    ExerciseEntity(
                        workoutId = workoutId,
                        name = exercise.name,
                        equipment = ""  // TODO: добавить equipment в модель Exercise
                    )
                )

                exercise.sets.forEach { set ->
                    dao.insertSet(
                        SetEntity(
                            exerciseId = exerciseId,
                            weight = set.weight,
                            reps = set.reps
                        )
                    )
                }
            }
        }

    // -------------------------------------------------------------------------
    // Notes

    suspend fun getNotesForDate(date: LocalDate): List<Note> =
        withContext(Dispatchers.IO) {
            val workout = dao.getWorkoutByDate(date.toString())
                ?: return@withContext emptyList()

            dao.getNotesForWorkout(workout.id).map { entity ->
                Note(id = entity.id.toInt(), text = entity.text)
            }
        }

    @androidx.room.Transaction
    suspend fun saveNotes(date: LocalDate, notes: List<Note>) =
        withContext(Dispatchers.IO) {
            val workoutId = getOrCreateWorkoutId(date)

            dao.deleteNotesForWorkout(workoutId)

            notes.forEach { note ->
                if (note.text.isBlank()) return@forEach
                dao.insertNote(
                    NoteEntity(
                        workoutId = workoutId,
                        text = note.text
                    )
                )
            }
        }
}