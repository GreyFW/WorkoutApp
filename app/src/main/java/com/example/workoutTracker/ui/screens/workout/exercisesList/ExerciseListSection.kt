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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.BlueField
import com.example.workouttracker.ui.theme.CustomFontFamily
import java.time.LocalDate
import java.util.UUID

@Composable
fun ExerciseListSection(selectedDate: LocalDate) {
    val exercises = remember(selectedDate) { mutableStateListOf(UUID.randomUUID().toString()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy((-2).dp)
    ) {
        exercises.forEach { id ->
            ExerciseInputRow(
                onDeleteExercise = {
                    exercises.remove(id)
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(BlueField, RoundedCornerShape(8.dp))
                .clickable {
                    exercises.add(UUID.randomUUID().toString())
                }
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