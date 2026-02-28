package com.example.workouttracker.ui.screens.workout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.ui.theme.*
import com.example.workouttracker.R

enum class DayBoxType { START, MID, END }
@Composable
fun PastDayBox(
    day: String,
    isTrained: Boolean,
    type: DayBoxType = DayBoxType.MID
) {
    val (width, height) = when (type) {
        DayBoxType.START -> 52.dp to 30.dp
        DayBoxType.MID -> 60.dp to 30.dp
        DayBoxType.END -> 51.dp to 30.dp
    }

    val backgroundRes = when (type) {
        DayBoxType.START -> R.drawable.ic_day_start
        DayBoxType.MID -> R.drawable.ic_day_middle
        DayBoxType.END -> R.drawable.ic_day_end
    }
    Box(
      modifier = Modifier
          .size(width = width, height = height)
          .paint(
              painter = painterResource(id = backgroundRes),
              contentScale = ContentScale.FillBounds,
              colorFilter = if (isTrained) ColorFilter.tint(BlueAccent) else null
          ),
        contentAlignment = Alignment.Center
    ) {
        Text (
            text = day,
            color = if (isTrained) White else BlueAccent,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily,
            modifier = Modifier.padding(
                end = if (type == DayBoxType.START) 6.dp else 0.dp,
                start = if (type == DayBoxType.END) 6.dp else 0.dp
            )
        )
    }
}