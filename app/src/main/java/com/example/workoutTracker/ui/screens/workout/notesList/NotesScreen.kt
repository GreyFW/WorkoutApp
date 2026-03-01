package com.example.workouttracker.ui.screens.workout.notesList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.models.Note
import com.example.workouttracker.ui.theme.AppDimens
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.BlueField
import com.example.workouttracker.ui.theme.CustomFontFamily

@Composable
fun NotesSection(
    notes: List<Note>,
    onAddNoteClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "NOTES",
            color = BlueAccent,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = CustomFontFamily
        )

        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

        notes.forEach { note ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(tween(400)) + expandVertically(tween(400))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .background(BlueField, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = note.text,
                        color = BlueAccent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = CustomFontFamily
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                .clickable { onAddNoteClick() }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "+",
                color = BlueAccent,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = CustomFontFamily
            )
        }
    }
}