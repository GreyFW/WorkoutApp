package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.workouttracker.data.prefs.WorkoutPrefs
import com.example.workouttracker.models.Note
import com.example.workouttracker.ui.screens.workout.exercisesList.ExerciseListSection
import com.example.workouttracker.ui.screens.workout.header.WorkoutScreenHeader
import com.example.workouttracker.ui.screens.workout.notesList.NotesList
import com.example.workouttracker.ui.theme.AppDimens
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.BlueBGDark
import java.time.LocalDate

@Composable
fun WorkoutScreen() {
    val context = LocalContext.current
    val prefs = remember { WorkoutPrefs(context) }

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentStreak by remember { mutableStateOf(prefs.streak) }
    var trainedDates by remember { mutableStateOf(prefs.getTrainedDates()) }

    val notesList = remember(selectedDate) { mutableStateListOf<Note>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueBGDark)
            .padding(
                top = AppDimens.paddingHuge,
                start = AppDimens.paddingMedium,
                end = AppDimens.paddingMedium,
                bottom = AppDimens.paddingMedium
            )
    ) {
        item {
            WorkoutScreenHeader(
                selectedDate = selectedDate,
                onDateChanged = { newDate -> selectedDate = newDate },
                currentStreak = currentStreak,
                trainedDates = trainedDates,
                onSaveWorkout = {
                    prefs.saveWorkout(selectedDate)
                    currentStreak = prefs.streak
                    trainedDates = prefs.getTrainedDates()
                }
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            HorizontalDivider(
                thickness = AppDimens.dividerThicknessStandard,
                color = BlueAccent
            )
        }

        item {
            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            ExerciseListSection(selectedDate = selectedDate)

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
        }

        item {
            HorizontalDivider(
                thickness = AppDimens.dividerThicknessStandard,
                color = BlueAccent
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            NotesList(
                notes = notesList,
                onAddNoteClick = {
                    val newId = (notesList.maxOfOrNull { it.id } ?: 0) + 1
                    notesList.add(Note(id = newId, text = ""))
                },
                onNoteTextChange = { id, newText ->
                    val index = notesList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        notesList[index] = notesList[index].copy(text = newText)
                    }
                }
            )
        }
    }
}