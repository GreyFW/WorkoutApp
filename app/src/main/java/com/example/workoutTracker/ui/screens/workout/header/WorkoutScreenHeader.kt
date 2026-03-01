package com.example.workouttracker.ui.screens.workout.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.AppDimens
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.CustomFontFamily
import com.example.workouttracker.utils.DateUtils
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun WorkoutScreenHeader(
    selectedDate: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
    currentStreak: Int,
    trainedDates: Set<String>,
    onSaveWorkout: () -> Unit
) {
    var startH by remember { mutableStateOf("00") }
    var startM by remember { mutableStateOf("00") }
    var endH by remember { mutableStateOf("99") }
    var endM by remember { mutableStateOf("99") }
    var isStartUntouched by remember { mutableStateOf(true) }
    var isEndUntouched by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderDates(
            selectedDate = selectedDate,
            trainedDates = trainedDates,
            onDateChanged = onDateChanged
        )

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
                text = "DAILY WORKOUT",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = CustomFontFamily,
                modifier = Modifier.padding(start = 24.dp, top = 3.dp)
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_streak_arrow),
                    contentDescription = null,
                    tint = BlueAccent,
                    modifier = Modifier
                        .size(AppDimens.iconSizeStandard)
                        .clickable { onSaveWorkout() }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderDates(
    selectedDate: LocalDate,
    trainedDates: Set<String>,
    onDateChanged: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val newDate = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
                        onDateChanged(newDate)
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = BlueAccent)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = BlueAccent)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val pastDays = DateUtils.getLastFourDays(selectedDate)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy((-8).dp),
                modifier = Modifier.weight(1f)
            ) {
                pastDays.forEachIndexed { index, date ->
                    val type = when (index) {
                        0 -> DayBoxType.START
                        3 -> DayBoxType.END
                        else -> DayBoxType.MID
                    }
                    val dayStr = date.dayOfMonth.toString().padStart(2, '0')
                    val isTrained = trainedDates.contains(date.toString())

                    Box(modifier = Modifier.clickable { onDateChanged(date) }) {
                        PastDayBox(
                            day = dayStr,
                            isTrained = isTrained,
                            type = type
                        )
                    }
                }
            }

            Text(
                text = DateUtils.getFormattedDate(selectedDate),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily,
                color = BlueAccent,
                modifier = Modifier
                    .padding(end = AppDimens.paddingExtraSmall)
                    .offset(x = 6.dp)
                    .clickable { showDatePicker = true }
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_today_line),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(200.dp)
                .height(16.dp)
                .align(Alignment.End)
                .offset(x = (50).dp, y = (-14).dp)
        )
    }
}