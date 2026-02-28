package com.example.workouttracker.ui.screens.workout

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.*

@Composable
fun WorkoutScreen() {
    var startTime by remember { mutableStateOf("16:00") }
    var endTime by remember { mutableStateOf("17:30") }
    val currentStreak = 12

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueBGDark)
            .padding(
                top = AppDimens.paddingHuge,
                start = AppDimens.paddingMedium,
                end = AppDimens.paddingMedium
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)) {
                PastDayBox(day = "24", isTrained = true)
                PastDayBox(day = "25", isTrained = false)
                PastDayBox(day = "26", isTrained = true)
                PastDayBox(day = "27", isTrained = true)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.mock_date),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = CustomFontFamily,
                    color = BlueAccent
                )
                HorizontalDivider(
                    modifier = Modifier.width(120.dp),
                    thickness = AppDimens.dividerThicknessThick,
                    color = BlueAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingExtraLarge))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueAccent, shape = RoundedCornerShape(8.dp))
                .padding(vertical = AppDimens.paddingMedium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.daily_workout),
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = CustomFontFamily
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // закинуть вектор иконки с часами
            // Icon(
            //    imageVector = Icons.Default.Schedule,
            //    contentDescription = null,
            //    tint = Color.Black,
            //    modifier = Modifier.size(AppDimens.iconSizeStandard)
            //)

            Spacer(modifier = Modifier.width(AppDimens.paddingSmall))

            BasicTextField(
                value = startTime,
                onValueChange = { startTime = it },
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BlueAccent)
            )

            Text(
                text = stringResource(id = R.string.time_separator),
                color = BlueAccent,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily
            )

            BasicTextField(
                value = endTime,
                onValueChange = { endTime = it },
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BlueAccent)
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = BlueAccent,
                modifier = Modifier.size(AppDimens.iconSizeStandard)
            )

            Spacer(modifier = Modifier.width(AppDimens.paddingSmall))

            Text(
                text = stringResource(id = R.string.streak_count, currentStreak),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily,
                color = BlueAccent
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

        HorizontalDivider(
            thickness = AppDimens.dividerThicknessStandard,
            color = BlueAccent
        )
    }
}