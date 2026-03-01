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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
    val maxRepsPerRow = 4

    var name by remember { mutableStateOf("") }
    var equipment by remember { mutableStateOf<EquipmentType?>(null) }
    var weight by remember { mutableStateOf("") }

    val savedReps = remember { mutableStateListOf<String>() }
    var currentRepInput by remember { mutableStateOf("") }

    var expandedDropdownIndex by remember { mutableStateOf<Int?>(null) }
    var showDeleteDialogForIndex by remember { mutableStateOf<Int?>(null) }

    val rowCount = (savedReps.size / maxRepsPerRow) + 1

    if (showDeleteDialogForIndex != null) {
        DeleteConfirmDialog(
            isFullExercise = showDeleteDialogForIndex == 0,
            onConfirm = {
                val index = showDeleteDialogForIndex!!
                if (index == 0) {
                    onDeleteExercise()
                } else {
                    val start = index * maxRepsPerRow
                    val end = minOf(start + maxRepsPerRow, savedReps.size)
                    savedReps.subList(start, end).clear()
                }
                showDeleteDialogForIndex = null
            },
            onDismiss = { showDeleteDialogForIndex = null }
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
                        painter = painterResource(id = R.drawable.ic_list_element_light_ver),
                        contentScale = ContentScale.FillBounds
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { showDeleteDialogForIndex = rowIndex }
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
                    ExerciseNameField(
                        name = name,
                        onNameChange = { name = it },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    if (name.isNotEmpty()) {
                        EquipmentSelector(
                            equipment = equipment,
                            isExpanded = expandedDropdownIndex == rowIndex,
                            onDropdownClick = { expandedDropdownIndex = rowIndex },
                            onDismiss = { expandedDropdownIndex = null },
                            onEquipmentSelect = {
                                equipment = it
                                if (it == EquipmentType.FW) weight = ""
                            }
                        )
                    } else {
                        Spacer(modifier = Modifier.width(48.dp))
                    }

                    WeightInputField(
                        equipment = equipment,
                        weight = weight,
                        onWeightChange = { weight = it }
                    )

                    RepsInputField(
                        equipment = equipment,
                        weight = weight,
                        rowReps = rowReps,
                        currentRepInput = currentRepInput,
                        rowIndex = rowIndex,
                        rowCount = rowCount,
                        maxRepsPerRow = maxRepsPerRow,
                        onRepInputChange = { currentRepInput = it },
                        onRepSubmit = {
                            if (currentRepInput.isNotEmpty()) {
                                savedReps.add(currentRepInput)
                                currentRepInput = ""
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseNameField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = name,
        onValueChange = { if (it.length <= 16) onNameChange(it) },
        textStyle = TextStyle(
            color = BlueAccent,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = CustomFontFamily
        ),
        cursorBrush = SolidColor(BlueAccent),
        singleLine = true,
        modifier = modifier,
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
}

@Composable
private fun EquipmentSelector(
    equipment: EquipmentType?,
    isExpanded: Boolean,
    onDropdownClick: () -> Unit,
    onDismiss: () -> Unit,
    onEquipmentSelect: (EquipmentType) -> Unit
) {
    Box(modifier = Modifier.width(48.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable { onDropdownClick() },
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
                    EquipmentType.DB -> Icon(painterResource(id = R.drawable.ic_dumbbells), null, tint = BlueAccent)
                    EquipmentType.BB -> Icon(painterResource(id = R.drawable.ic_barbell), null, tint = BlueAccent)
                    EquipmentType.FW, EquipmentType.P -> Spacer(modifier = Modifier.width(54.dp))
                }
            }
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onDismiss,
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
                        onEquipmentSelect(eqpType)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
private fun WeightInputField(
    equipment: EquipmentType?,
    weight: String,
    onWeightChange: (String) -> Unit
) {
    Box(
        modifier = Modifier.width(64.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (equipment != null && equipment != EquipmentType.FW) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    value = weight,
                    onValueChange = { newValue ->
                        val digits = newValue.filter { it.isDigit() }
                        if (digits.length <= 3) onWeightChange(digits)
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
}

@Composable
private fun RepsInputField(
    equipment: EquipmentType?,
    weight: String,
    rowReps: List<String>,
    currentRepInput: String,
    rowIndex: Int,
    rowCount: Int,
    maxRepsPerRow: Int,
    onRepInputChange: (String) -> Unit,
    onRepSubmit: () -> Unit
) {
    Box(
        modifier = Modifier.width(100.dp).offset(x = (-6).dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (equipment == EquipmentType.FW || equipment == EquipmentType.P || (equipment != null && weight.isNotEmpty())) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                            val digits = newValue.filter { it.isDigit() }
                            if (digits.length <= 3) onRepInputChange(digits)
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
                        keyboardActions = KeyboardActions(onDone = { onRepSubmit() }),
                        singleLine = true,
                        cursorBrush = SolidColor(BlueAccent),
                        modifier = Modifier.width(32.dp),
                        decorationBox = { innerTextField ->
                            if (currentRepInput.isEmpty() && rowReps.isEmpty()) {
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