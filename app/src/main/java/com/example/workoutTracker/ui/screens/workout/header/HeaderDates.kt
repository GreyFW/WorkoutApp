package com.example.workouttracker.ui.screens.workout.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.*

@Composable
fun HeaderDates(currentDate: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row (
                horizontalArrangement = Arrangement.spacedBy((-8).dp),
                modifier = Modifier.weight(1f)
            ) {
                PastDayBox(day = "31", isTrained = true, type = DayBoxType.START)
                PastDayBox(day = "01", isTrained = true)
                PastDayBox(day = "02", isTrained = false)
                PastDayBox(day = "03", isTrained = false, type = DayBoxType.END)
            }

            Text(
                text = currentDate,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily,
                color = BlueAccent,
                modifier = Modifier.padding(end = AppDimens.paddingExtraSmall)
                    .offset(x = 6.dp)
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
