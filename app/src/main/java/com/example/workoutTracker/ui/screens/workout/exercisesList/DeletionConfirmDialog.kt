package com.example.workouttracker.ui.screens.workout.exercisesList

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.CustomFontFamily

@Composable
fun DeleteConfirmDialog(
    isFullExercise: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Delete",
                color = BlueAccent,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = if (isFullExercise) "Remove this exercise entirely?" else "Remove these reps?",
                fontFamily = CustomFontFamily
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes", color = Color.Red, fontFamily = CustomFontFamily, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = BlueAccent, fontFamily = CustomFontFamily)
            }
        }
    )
}