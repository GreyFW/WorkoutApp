package com.example.workouttracker.ui.screens.workout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.workouttracker.data.AppDatabase
import com.example.workouttracker.data.prefs.WorkoutPrefs
import com.example.workouttracker.data.WorkoutRepository
import com.example.workouttracker.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = WorkoutPrefs(application)
    private val dao = AppDatabase.getDatabase(application).workoutDao()
    private val repository = WorkoutRepository(dao)

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentStreak = MutableStateFlow(prefs.streak)
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()

    private val _trainedDates = MutableStateFlow(prefs.getTrainedDates())
    val trainedDates: StateFlow<Set<String>> = _trainedDates.asStateFlow()

    // Временное хранение заметок в памяти для UI
    private val _notesList = MutableStateFlow<List<Note>>(emptyList())
    val notesList: StateFlow<List<Note>> = _notesList.asStateFlow()

    fun setDate(date: LocalDate) {
        _selectedDate.value = date
        // Позже repository.getWorkoutByDate(date)
    }

    fun saveWorkout() {
        prefs.saveWorkout(_selectedDate.value)
        _currentStreak.value = prefs.streak
        _trainedDates.value = prefs.getTrainedDates()
    }

    fun addNote() {
        val currentList = _notesList.value.toMutableList()
        val newId = (currentList.maxOfOrNull { it.id } ?: 0) + 1
        currentList.add(Note(id = newId, text = ""))
        _notesList.value = currentList
    }

    fun updateNoteText(id: Int, newText: String) {
        val currentList = _notesList.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(text = newText)
            _notesList.value = currentList
        }
    }
}