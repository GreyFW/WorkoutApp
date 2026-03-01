package com.example.workouttracker.ui.screens.workout.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.*

@Composable
fun WorkoutScreenHeader() {
    var startH by remember { mutableStateOf("00") }
    var startM by remember { mutableStateOf("00") }
    var endH by remember { mutableStateOf("99") }
    var endM by remember { mutableStateOf("99") }
    var isStartUntouched by remember { mutableStateOf(true) }
    var isEndUntouched by remember { mutableStateOf(true) }

    val currentStreak = 4

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderDates(currentDate = "28 feb. saturday")

        Spacer(modifier = Modifier.height(AppDimens.paddingExtraSmall))

        // BANNER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .paint(
                    painter = painterResource(id = R.drawable.ic_banner_title),
                    contentScale = ContentScale.FillBounds,
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.daily_workout).uppercase(),
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = CustomFontFamily,
                modifier = Modifier.padding(start = 24.dp, top = 3.dp)
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

        // ROW TIME + STREAK
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // TIME-ROW
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_timer),
                    contentDescription = null,
                    tint = BlueAccent,
                    modifier = Modifier.size(AppDimens.iconSizeStandard)
                )
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))

                TimeInputField(
                    hour = startH,
                    minute = startM,
                    isUntouched = isStartUntouched
                ) { h, m ->
                    startH = h
                    startM = m
                    if (isStartUntouched) isStartUntouched = false
                }

                Text(" — ", color = BlueAccent, fontWeight = FontWeight.Bold)

                TimeInputField(
                    hour = endH,
                    minute = endM,
                    isUntouched = isEndUntouched
                ) { h, m ->
                    endH = h
                    endM = m
                    if (isEndUntouched) isEndUntouched = false
                }
            }
            // STREAK-ROW
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_streak_arrow),
                    contentDescription = null,
                    tint = BlueAccent,
                    modifier = Modifier.size(AppDimens.iconSizeStandard)
                )
                Text(
                    text = "STREAK: $currentStreak",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = CustomFontFamily,
                    color = BlueAccent,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
