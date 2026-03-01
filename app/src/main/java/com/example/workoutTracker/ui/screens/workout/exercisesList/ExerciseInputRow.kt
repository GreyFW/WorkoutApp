package com.example.workouttracker.ui.screens.workout.exercisesList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.models.EquipmentType
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.CustomFontFamily
import com.example.workouttracker.ui.theme.TextGray

@Composable
fun ExerciseInputRow(
    onDeleteExercise: () -> Unit
) {
    val exerciseNameLimit = 16
    val weightLimit = 3
    val repsLimit = 3
    val maxRepsPerRow = 3

    val nameFieldPaddingEnd = 4.dp
    val equipmentBoxWidth = 48.dp
    val spacerBeforeWeight = 16.dp
    val weightBoxWidth = 64.dp
    val spacerBeforeReps = 8.dp
    val repsBoxWidth = 100.dp

    var name by remember { mutableStateOf("") }
    var equipment by remember { mutableStateOf<EquipmentType?>(null) }
    var weight by remember { mutableStateOf("") }

    val savedReps = remember { mutableStateListOf<String>() }
    var currentRepInput by remember { mutableStateOf("") }

    var expandedDropdownIndex by remember { mutableStateOf<Int?>(null) }
    var showDeleteDialogForIndex by remember { mutableStateOf<Int?>(null) }

    val rowCount = (savedReps.size / maxRepsPerRow) + 1

    if (showDeleteDialogForIndex != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialogForIndex = null },
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
                    text = if (showDeleteDialogForIndex == 0) "Remove this exercise entirely?" else "Remove these reps?",
                    fontFamily = CustomFontFamily
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val index = showDeleteDialogForIndex!!
                        if (index == 0) {
                            onDeleteExercise()
                        } else {
                            val start = index * maxRepsPerRow
                            val end = minOf(start + maxRepsPerRow, savedReps.size)
                            savedReps.subList(start, end).clear()
                        }
                        showDeleteDialogForIndex = null
                    }
                ) {
                    Text("Yes", color = Color.Red, fontFamily = CustomFontFamily, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialogForIndex = null }) {
                    Text("Cancel", color = BlueAccent, fontFamily = CustomFontFamily)
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy((-2).dp)
    ) {
        for (rowIndex in 0 until rowCount) {
            val startIndex = rowIndex * maxRepsPerRow
            val endIndex = minOf(startIndex + maxRepsPerRow, savedReps.size)
            val rowReps = savedReps.subList(startIndex, endIndex)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .paint(
                        painter = painterResource(id = R.drawable.ic_list_element),
                        contentScale = ContentScale.FillBounds
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                showDeleteDialogForIndex = rowIndex
                            }
                        )
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 24.dp)
                ) {
                    BasicTextField(
                        value = name,
                        onValueChange = { newValue ->
                            if (newValue.length <= exerciseNameLimit) name = newValue
                        },
                        textStyle = TextStyle(
                            color = BlueAccent,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = CustomFontFamily
                        ),
                        cursorBrush = SolidColor(BlueAccent),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = nameFieldPaddingEnd),
                        decorationBox = { innerTextField ->
                            if (name.isEmpty()) {
                                Text(
                                    text = "name...",
                                    color = TextGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = CustomFontFamily
                                )
                            }
                            innerTextField()
                        }
                    )

                    if (name.isNotEmpty()) {
                        Box(
                            modifier = Modifier.width(equipmentBoxWidth)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .clickable { expandedDropdownIndex = rowIndex },
                                contentAlignment = Alignment.Center
                            ) {
                                if (equipment == null) {
                                    Text(
                                        text = "EQP",
                                        color = TextGray,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = CustomFontFamily
                                    )
                                } else {
                                    when (equipment) {
                                        EquipmentType.DB -> {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_dumbbells),
                                                contentDescription = null,
                                                tint = BlueAccent
                                            )
                                        }
                                        EquipmentType.BB -> {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_barbell),
                                                contentDescription = null,
                                                tint = BlueAccent
                                            )
                                        }
                                        EquipmentType.FW, EquipmentType.P -> {
                                        }
                                        null -> {}
                                    }
                                }
                            }

                            DropdownMenu(
                                expanded = expandedDropdownIndex == rowIndex,
                                onDismissRequest = { expandedDropdownIndex = null },
                                modifier = Modifier.background(Color.White)
                            ) {
                                EquipmentType.entries.forEach { eqpType ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = eqpType.label,
                                                color = BlueAccent,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = CustomFontFamily
                                            )
                                        },
                                        onClick = {
                                            equipment = eqpType
                                            expandedDropdownIndex = null
                                            if (eqpType == EquipmentType.FW) {
                                                weight = ""
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.width(equipmentBoxWidth))
                    }

                    Spacer(modifier = Modifier.width(spacerBeforeWeight))

                    Box(
                        modifier = Modifier.width(weightBoxWidth),
                        contentAlignment = Alignment.Center
                    ) {
                        if (equipment != null && equipment != EquipmentType.FW) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                BasicTextField(
                                    value = weight,
                                    onValueChange = { newValue ->
                                        val digitsOnly = newValue.filter { it.isDigit() }
                                        if (digitsOnly.length <= weightLimit) weight = digitsOnly
                                    },
                                    textStyle = TextStyle(
                                        color = BlueAccent,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = CustomFontFamily,
                                        textAlign = TextAlign.End
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    cursorBrush = SolidColor(BlueAccent),
                                    modifier = Modifier.width(28.dp),
                                    decorationBox = { innerTextField ->
                                        if (weight.isEmpty()) {
                                            Text(
                                                text = "0",
                                                color = TextGray,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = CustomFontFamily,
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                        innerTextField()
                                    }
                                )
                                Text(
                                    text = "kg",
                                    color = BlueAccent,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = CustomFontFamily
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(spacerBeforeReps))

                    Box(
                        modifier = Modifier.width(repsBoxWidth),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (equipment == EquipmentType.FW || equipment == EquipmentType.P || (equipment != null && weight.isNotEmpty())) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (rowReps.isNotEmpty()) {
                                    val needsDash = if (rowReps.size < maxRepsPerRow && rowIndex == rowCount - 1) " - " else ""
                                    Text(
                                        text = rowReps.joinToString(" - ") + needsDash,
                                        color = BlueAccent,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = CustomFontFamily
                                    )
                                }

                                if (rowIndex == rowCount - 1 && rowReps.size < maxRepsPerRow) {
                                    BasicTextField(
                                        value = currentRepInput,
                                        onValueChange = { newValue ->
                                            val digitsOnly = newValue.filter { it.isDigit() }
                                            if (digitsOnly.length <= repsLimit) currentRepInput = digitsOnly
                                        },
                                        textStyle = TextStyle(
                                            color = BlueAccent,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontFamily = CustomFontFamily
                                        ),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = {
                                                if (currentRepInput.isNotEmpty()) {
                                                    savedReps.add(currentRepInput)
                                                    currentRepInput = ""
                                                }
                                            }
                                        ),
                                        singleLine = true,
                                        cursorBrush = SolidColor(BlueAccent),
                                        modifier = Modifier.width(32.dp),
                                        decorationBox = { innerTextField ->
                                            if (currentRepInput.isEmpty() && savedReps.isEmpty()) {
                                                Text(
                                                    text = "reps",
                                                    color = TextGray,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontFamily = CustomFontFamily
                                                )
                                            }
                                            innerTextField()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}