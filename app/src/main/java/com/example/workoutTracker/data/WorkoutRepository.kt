package com.example.workouttracker.data

import com.example.workouttracker.data.entities.NoteEntity
import com.example.workouttracker.data.entities.WorkoutDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkoutRepository(private val dao: WorkoutDao) {

    suspend fun insertNote(note: NoteEntity) {
        withContext(Dispatchers.IO) {
            dao.insertNote(note)
        }
    }

    suspend fun getNotesForWorkout(workoutId: Long): List<NoteEntity> {
        return withContext(Dispatchers.IO) {
            dao.getNotesForWorkout(workoutId)
        }
    }
}