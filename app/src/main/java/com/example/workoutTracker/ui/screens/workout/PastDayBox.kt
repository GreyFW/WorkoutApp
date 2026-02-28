package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.ui.theme.*

@Composable
fun PastDayBox(day: String, isTrained: Boolean) {
    Box(
      modifier = Modifier
          .size(AppDimens.dayBoxSize)
          .background(
              color = if (isTrained) BlueAccent else Color.Transparent,
              shape = RoundedCornerShape(4.dp)
          )
          .border(
              width = 1.dp,
              color = BlueAccent,
              shape = RoundedCornerShape(4.dp)
          ),
        contentAlignment = Alignment.Center
    ) {
        Text (
            text = day,
            color = if (isTrained) White else BlueAccent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}