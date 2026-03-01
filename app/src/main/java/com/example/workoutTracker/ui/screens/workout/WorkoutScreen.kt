package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workouttracker.models.Exercise
import com.example.workouttracker.models.Note
import com.example.workouttracker.models.WorkoutSet
import com.example.workouttracker.ui.screens.workout.exercisesList.ExerciseListSection
import com.example.workouttracker.ui.theme.*
import com.example.workouttracker.ui.screens.workout.header.WorkoutScreenHeader
import com.example.workouttracker.ui.screens.workout.notesList.NotesSection

@Composable
fun WorkoutScreen() {
    val notesList = remember { mutableStateListOf<Note>() }

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
            WorkoutScreenHeader()

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            HorizontalDivider(
                thickness = AppDimens.dividerThicknessStandard,
                color = BlueAccent
            )
        }

        item {
            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            ExerciseListSection()

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
        }

        item {
            HorizontalDivider(
                thickness = AppDimens.dividerThicknessStandard,
                color = BlueAccent
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            NotesSection(
                notes = notesList,
                onAddNoteClick = {
                    val newId = notesList.size + 1
                    notesList.add(Note(id = newId, text = "New auto-generated note $newId"))
                }
            )
        }
    }
}