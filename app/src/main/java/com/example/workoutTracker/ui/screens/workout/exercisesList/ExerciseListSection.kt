package com.example.workouttracker.ui.screens.workout.exercisesList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.models.Exercise
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.BlueField
import com.example.workouttracker.ui.theme.CustomFontFamily

@Composable
fun ExerciseListSection(
    exercises: List<Exercise>,
    onAddExercise: () -> Unit,
    onDeleteExercise: (id: Int) -> Unit,
    onNameChange: (id: Int, name: String) -> Unit,
    onAddSet: (exerciseId: Int, weight: String, reps: String) -> Unit,
    onDeleteSetRow: (exerciseId: Int, fromIndex: Int, count: Int) -> Unit,
    onToggleSet: (exerciseId: Int, setId: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy((-2).dp)
    ) {
        exercises.forEach { exercise ->
            ExerciseInputRow(
                exercise = exercise,
                onDeleteExercise = { onDeleteExercise(exercise.id) },
                onNameChange = { name -> onNameChange(exercise.id, name) },
                onAddSet = { weight, reps -> onAddSet(exercise.id, weight, reps) },
                onDeleteSetRow = { fromIndex, count ->
                    onDeleteSetRow(exercise.id, fromIndex, count)
                },
                onToggleSet = { setId -> onToggleSet(exercise.id, setId) }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(BlueField, RoundedCornerShape(8.dp))
                .clickable { onAddExercise() }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+ ADD EXERCISE",
                color = BlueAccent,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = CustomFontFamily
            )
        }
    }
}