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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
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
import com.example.workouttracker.models.Exercise
import com.example.workouttracker.models.WorkoutSet
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.CustomFontFamily
import com.example.workouttracker.ui.theme.TextGray

private fun capacityFor(firstReps: String): Int = when {
    firstReps.length >= 3 -> 2
    firstReps.length == 2 -> 3
    else                  -> 4
}

private fun sliceIntoRows(sets: List<WorkoutSet>): List<List<WorkoutSet>> {
    val rows = mutableListOf<List<WorkoutSet>>()
    var i = 0
    while (i < sets.size) {
        val capacity = capacityFor(sets[i].reps)
        rows.add(sets.subList(i, minOf(i + capacity, sets.size)))
        i += capacity
    }
    return rows
}

@Composable
fun ExerciseInputRow(
    exercise: Exercise,
    onDeleteExercise: () -> Unit,
    onNameChange: (String) -> Unit,
    onAddSet: (weight: String, reps: String) -> Unit,
    onDeleteSetRow: (fromIndex: Int, count: Int) -> Unit,
    onToggleSet: (setId: Int) -> Unit
) {
    var currentRepInput by remember(exercise.id) { mutableStateOf("") }
    var equipment by remember(exercise.id) { mutableStateOf<EquipmentType?>(null) }
    var weight by remember(exercise.id) { mutableStateOf("") }

    var expandedDropdownIndex by remember { mutableStateOf<Int?>(null) }
    var showDeleteDialogForIndex by remember { mutableStateOf<Int?>(null) }
    val inputRowDismissedState = remember(exercise.id) { mutableStateOf(false) }
    var inputRowDismissed by inputRowDismissedState

    val sliced = sliceIntoRows(exercise.sets)
    val lastRowFull = sliced.isNotEmpty() && run {
        val last = sliced.last()
        last.size >= capacityFor(last.first().reps)
    }

    LaunchedEffect(lastRowFull) {
        if (lastRowFull) inputRowDismissedState.value = false
    }

    val rows: List<List<WorkoutSet>> = if ((sliced.isEmpty() || lastRowFull) && !inputRowDismissed) {
        sliced + listOf(emptyList())
    } else {
        sliced
    }
    val rowCount = rows.size

    val inputRowCapacity = if (currentRepInput.isNotEmpty()) capacityFor(currentRepInput) else 4

    if (showDeleteDialogForIndex != null) {
        val index = showDeleteDialogForIndex!!
        DeleteConfirmDialog(
            isFullExercise = index == 0,
            onConfirm = {
                if (index == 0) {
                    onDeleteExercise()
                } else {
                    val fromIndex = rows.take(index).sumOf { it.size }
                    val count = rows[index].size
                    onDeleteSetRow(fromIndex, count)
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
        rows.forEachIndexed { rowIndex, rowSets ->
            val isLastRow = rowIndex == rowCount - 1
            val maxRepsPerRow = when {
                rowSets.isNotEmpty() -> capacityFor(rowSets.first().reps)
                else                 -> inputRowCapacity
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .paint(
                        painter = painterResource(id = R.drawable.ic_list_element_light_ver),
                        contentScale = ContentScale.FillBounds
                    )
                    .pointerInput(rowSets.isEmpty(), inputRowDismissedState) {
                        detectTapGestures(
                            onLongPress = {
                                android.util.Log.d("ExerciseRow", "longPress rowIndex=$rowIndex isEmpty=${rowSets.isEmpty()}")
                                if (rowSets.isEmpty()) {
                                    inputRowDismissedState.value = true
                                } else {
                                    showDeleteDialogForIndex = rowIndex
                                }
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
                    ExerciseNameField(
                        name = exercise.name,
                        onNameChange = onNameChange,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    if (exercise.name.isNotEmpty()) {
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
                        rowSets = rowSets,
                        currentRepInput = currentRepInput,
                        isLastRow = isLastRow,
                        maxRepsPerRow = maxRepsPerRow,
                        onRepInputChange = { currentRepInput = it },
                        onRepSubmit = {
                            if (currentRepInput.isNotEmpty()) {
                                onAddSet(weight, currentRepInput)
                                currentRepInput = ""
                            }
                        }
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// Приватные подкомпоненты

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
                    EquipmentType.DB -> Icon(
                        painterResource(id = R.drawable.ic_dumbbells),
                        contentDescription = null,
                        tint = BlueAccent
                    )
                    EquipmentType.BB -> Icon(
                        painterResource(id = R.drawable.ic_barbell),
                        contentDescription = null,
                        tint = BlueAccent
                    )
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
        if (equipment != null && equipment != EquipmentType.FW && equipment != EquipmentType.P) {
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
    rowSets: List<WorkoutSet>,
    currentRepInput: String,
    isLastRow: Boolean,
    maxRepsPerRow: Int,
    onRepInputChange: (String) -> Unit,
    onRepSubmit: () -> Unit
) {
    val shouldShow = equipment == EquipmentType.FW
            || equipment == EquipmentType.P
            || (equipment != null && weight.isNotEmpty())

    Box(
        modifier = Modifier
            .width(100.dp)
            .offset(x = (-6).dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (shouldShow) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (rowSets.isNotEmpty()) {
                    val needsDash = isLastRow && rowSets.size < maxRepsPerRow
                    Text(
                        text = rowSets.joinToString(" - ") { it.reps } + if (needsDash) " - " else "",
                        color = BlueAccent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = CustomFontFamily
                    )
                }

                if (isLastRow && rowSets.size < maxRepsPerRow) {
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
                            if (currentRepInput.isEmpty() && rowSets.isEmpty()) {
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