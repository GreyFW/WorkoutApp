package com.example.workouttracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.*

@Composable
fun LevelProgressBar(
    currentLevel: Int,
    maxLevel: Int,
    modifier: Modifier = Modifier
) {
    val levelValueStr = currentLevel.toString()
    val fullLevelText = stringResource(id = R.string.current_level, currentLevel)
    val levelAnnotated = buildAnnotatedString {
        append(fullLevelText)
        val startIndex = fullLevelText.indexOf(levelValueStr)
        if (startIndex >= 0) {
            addStyle(
                style = SpanStyle(
                    color = BlueAccent,
                    fontWeight = FontWeight.ExtraBold
                ),
                start = startIndex,
                end = startIndex + levelValueStr.length
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = levelAnnotated,
            color = BlueAccentSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = CustomFontFamily
        )

        Spacer(modifier = Modifier.width(AppDimens.paddingMedium))

        val progressFraction = (currentLevel.toFloat() / maxLevel.toFloat()).coerceIn(0f, 1f)

        Box(
            modifier = Modifier
                .weight(1f)
                .height(16.dp)
                .border(
                    width = 2.dp,
                    color = BlueAccentSecondary,
                    shape = RoundedCornerShape(4.dp)
                )
                .clip(RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = progressFraction)
                    .background(BlueAccent)
            )
        }
    }
}