package com.example.workouttracker.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var isAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isAnimated = true
    }

    val targetFraction = (currentLevel.toFloat() / maxLevel.toFloat()).coerceIn(0f, 1f)

    val animatedFraction by animateFloatAsState(
        targetValue = if (isAnimated) targetFraction else 0f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "progressAnimation"
    )

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

        val parentShape = RoundedCornerShape(4.dp)

        val fillShape = RoundedCornerShape(
            topStart = 4.dp,
            bottomStart = 4.dp,
            topEnd = if (animatedFraction == 1f) 4.dp else 0.dp,
            bottomEnd = if (animatedFraction == 1f) 4.dp else 0.dp
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = BlueAccentSecondary,
                        shape = parentShape
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = animatedFraction)
                    .background(
                        color = BlueAccent,
                        shape = fillShape
                    )
                    .border(
                        width = 2.dp,
                        color = BlueAccent,
                        shape = fillShape
                    )
            )
        }
    }
}