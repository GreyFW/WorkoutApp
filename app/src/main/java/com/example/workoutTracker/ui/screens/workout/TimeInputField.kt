package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.ui.theme.*

@Composable
fun TimeInputField(
    hour: String,
    minute: String,
    onValueChange: (String, String) -> Unit
) {
    val isPlaceholder = hour == "00" && minute == "00"
    val textColor = if (isPlaceholder) TextGray else BlueAccent

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(BlueField, RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        BasicTextField(
            value = hour,
            onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) onValueChange(it, minute) },
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily,
                color = textColor,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(24.dp)
        )
        Text(
            text = ":",
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        BasicTextField(
            value = minute,
            onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) onValueChange(hour, it) },
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily,
                color = textColor,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(28.dp)
        )
    }
}