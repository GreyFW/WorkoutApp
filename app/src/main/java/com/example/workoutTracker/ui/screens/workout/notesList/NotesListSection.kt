package com.example.workouttracker.ui.screens.workout.notesList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.models.Note
import com.example.workouttracker.ui.theme.AppDimens
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.BlueField
import com.example.workouttracker.ui.theme.CustomFontFamily
import com.example.workouttracker.ui.theme.TextGray

@Composable
fun NotesList(
    notes: List<Note>,
    onAddNoteClick: () -> Unit,
    onNoteTextChange: (Int, String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "NOTES",
                color = BlueAccent,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = CustomFontFamily
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(tween(400)),
            exit = shrinkVertically(tween(400))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                notes.forEach { note ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .background(BlueField, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        BasicTextField(
                            value = note.text,
                            onValueChange = { newText ->
                                onNoteTextChange(note.id, newText)
                            },
                            textStyle = TextStyle(
                                color = BlueAccent,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = CustomFontFamily
                            ),
                            cursorBrush = SolidColor(BlueAccent),
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                if (note.text.isEmpty()) {
                                    Text(
                                        text = "Write your note here...",
                                        color = TextGray,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = CustomFontFamily
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(BlueField, RoundedCornerShape(4.dp))
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
    }
}