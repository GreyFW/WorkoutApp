package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttracker.ui.screens.workout.exercisesList.ExerciseListSection
import com.example.workouttracker.ui.screens.workout.header.WorkoutScreenHeader
import com.example.workouttracker.ui.screens.workout.notesList.NotesList
import com.example.workouttracker.ui.theme.AppDimens
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.BlueBGDark

@Composable
fun WorkoutScreen(
    vm: WorkoutViewModel = viewModel()
) {
    val selectedDate by vm.selectedDate.collectAsState()
    val currentStreak by vm.currentStreak.collectAsState()
    val trainedDates by vm.trainedDates.collectAsState()
    val notesList by vm.notesList.collectAsState()
    val exerciseList by vm.exerciseList.collectAsState()

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
                onDateChanged = { vm.setDate(it) },
                currentStreak = currentStreak,
                trainedDates = trainedDates,
                onSaveWorkout = {
                    vm.saveWorkout()
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

            ExerciseListSection(
                exercises = exerciseList,
                onAddExercise = { vm.addExercise() },
                onDeleteExercise = { id -> vm.removeExercise(id) },
                onNameChange = { id, name -> vm.updateExerciseName(id, name) },
                onAddSet = { exerciseId, weight, reps ->
                    vm.addSetToExercise(exerciseId, weight, reps)
                },
                onDeleteSetRow = { exerciseId, fromIndex, count ->
                    vm.removeSetRow(exerciseId, fromIndex, count)
                },
                onToggleSet = { exerciseId, setId ->
                    vm.toggleSetCompleted(exerciseId, setId)
                }
            )

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
                onAddNoteClick = { vm.addNote() },
                onNoteTextChange = { id, text -> vm.updateNoteText(id, text) }
            )
        }
    }
}