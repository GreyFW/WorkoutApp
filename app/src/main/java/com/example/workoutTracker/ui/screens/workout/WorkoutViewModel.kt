package com.example.workouttracker.ui.screens.workout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.AppDatabase
import com.example.workouttracker.data.WorkoutRepository
import com.example.workouttracker.data.prefs.WorkoutPrefs
import com.example.workouttracker.models.Exercise
import com.example.workouttracker.models.Note
import com.example.workouttracker.models.WorkoutSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = WorkoutPrefs(application)

    private val repository = WorkoutRepository(
        AppDatabase.getDatabase(application).workoutDao()
    )

    // -------------------------------------------------------------------------
    // State

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentStreak = MutableStateFlow(prefs.streak)
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()

    private val _trainedDates = MutableStateFlow(prefs.getTrainedDates())
    val trainedDates: StateFlow<Set<String>> = _trainedDates.asStateFlow()

    private val _notesList = MutableStateFlow<List<Note>>(emptyList())
    val notesList: StateFlow<List<Note>> = _notesList.asStateFlow()

    private val _exerciseList = MutableStateFlow<List<Exercise>>(emptyList())
    val exerciseList: StateFlow<List<Exercise>> = _exerciseList.asStateFlow()

    // -------------------------------------------------------------------------
    // Date

    fun setDate(date: LocalDate) {
        _selectedDate.value = date
        loadDataForDate(date)
    }

    private fun loadDataForDate(date: LocalDate) {
        viewModelScope.launch {
            _exerciseList.value = repository.getExercisesForDate(date)
            _notesList.value = repository.getNotesForDate(date)
        }
    }

    // -------------------------------------------------------------------------
    // Streak

    fun saveWorkout() {
        prefs.saveWorkout(_selectedDate.value)
        _currentStreak.value = prefs.streak
        _trainedDates.value = prefs.getTrainedDates()

        viewModelScope.launch {
            repository.saveExercises(_selectedDate.value, _exerciseList.value)
            repository.saveNotes(_selectedDate.value, _notesList.value)
        }
    }

    // -------------------------------------------------------------------------
    // Exercises

    fun addExercise() {
        val newId = (_exerciseList.value.maxOfOrNull { it.id } ?: 0) + 1
        _exerciseList.value = _exerciseList.value + Exercise(
            id = newId,
            name = "",
            sets = emptyList()
        )
    }

    fun removeExercise(id: Int) {
        _exerciseList.value = _exerciseList.value.filter { it.id != id }
    }

    fun updateExerciseName(id: Int, name: String) {
        _exerciseList.value = _exerciseList.value.map { exercise ->
            if (exercise.id == id) exercise.copy(name = name) else exercise
        }
    }

    fun addSetToExercise(exerciseId: Int, weight: String, reps: String) {
        _exerciseList.value = _exerciseList.value.map { exercise ->
            if (exercise.id != exerciseId) return@map exercise
            val newSetId = (exercise.sets.maxOfOrNull { it.id } ?: 0) + 1
            exercise.copy(
                sets = exercise.sets + WorkoutSet(
                    id = newSetId,
                    weight = weight,
                    reps = reps
                )
            )
        }
    }

    fun removeSetRow(exerciseId: Int, fromIndex: Int, count: Int) {
        _exerciseList.value = _exerciseList.value.map { exercise ->
            if (exercise.id != exerciseId) return@map exercise
            val newSets = exercise.sets.toMutableList()
            val end = minOf(fromIndex + count, newSets.size)
            newSets.subList(fromIndex, end).clear()
            exercise.copy(sets = newSets)
        }
    }

    fun toggleSetCompleted(exerciseId: Int, setId: Int) {
        _exerciseList.value = _exerciseList.value.map { exercise ->
            if (exercise.id != exerciseId) return@map exercise
            exercise.copy(
                sets = exercise.sets.map { set ->
                    if (set.id == setId) set.copy(isCompleted = !set.isCompleted) else set
                }
            )
        }
    }

    // -------------------------------------------------------------------------
    // Notes

    fun addNote() {
        val newId = (_notesList.value.maxOfOrNull { it.id } ?: 0) + 1
        _notesList.value = _notesList.value + Note(id = newId, text = "")
    }

    fun updateNoteText(id: Int, newText: String) {
        _notesList.value = _notesList.value.map { note ->
            if (note.id == id) note.copy(text = newText) else note
        }
    }
}