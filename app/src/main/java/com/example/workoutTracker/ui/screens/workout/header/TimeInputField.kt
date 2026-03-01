package com.example.workouttracker.ui.screens.workout.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.ui.theme.*

@Composable
fun TimeInputField(
    hour: String,
    minute: String,
    isUntouched: Boolean,
    onValueChange: (String, String) -> Unit
) {
    var inputBuffer by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isFocused) {
        if (!isFocused) {
            inputBuffer = hour + minute
        }
    }

    Box(
        modifier = Modifier
            .background(BlueField, RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { focusRequester.requestFocus() }
        ) {
            TimeDigit(
                text = hour,
                isUntouched = isUntouched,
                modifier = Modifier.width(28.dp)
            )

            Text(
                text = ":",
                color = if (isUntouched) TextGray else BlueAccent,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            TimeDigit(
                text = minute,
                isUntouched = isUntouched,
                modifier = Modifier.width(28.dp)
            )
        }

        // Невидимое поле ввода
        BasicTextField(
            value = inputBuffer,
            onValueChange = { newValue ->
                val digits = newValue.filter { char -> char.isDigit() }

                when (digits.length) {
                    0 -> {
                        inputBuffer = ""
                        onValueChange("00", "00")
                    }
                    1 -> {
                        inputBuffer = digits
                        onValueChange(digits.padEnd(2, '0'), "00")
                    }
                    2 -> {
                        inputBuffer = digits
                        onValueChange(digits, "00")
                    }
                    3 -> {
                        inputBuffer = digits
                        val hours = digits.take(2)
                        val minutes = digits.last().toString().padEnd(2, '0')
                        onValueChange(hours, minutes)
                    }
                    4 -> {
                        inputBuffer = digits
                        onValueChange(digits.take(2), digits.drop(2))
                    }
                    else -> {
                        // 5+ цифр - циклическая запись
                        val lastFour = digits.takeLast(4)
                        inputBuffer = lastFour
                        onValueChange(lastFour.take(2), lastFour.drop(2))
                    }
                }
            },
            modifier = Modifier
                .matchParentSize()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            textStyle = TextStyle(fontSize = 1.sp, color = Color.Transparent),
            cursorBrush = SolidColor(Color.Transparent),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None,
            decorationBox = { innerTextField ->
                innerTextField()
            }
        )
    }
}

@Composable
private fun TimeDigit(
    text: String,
    isUntouched: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = CustomFontFamily,
        color = if (isUntouched) TextGray else BlueAccent,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}