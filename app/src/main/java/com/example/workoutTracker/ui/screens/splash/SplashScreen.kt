package com.example.workouttracker.ui.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.*

@Composable
fun SplashScreen() {
    val quotes = stringArrayResource(id = R.array.splash_quotes)
    val randomQuote = remember { quotes.random() }
    val currentStreak = 12
    val currentLevel = 4

    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_animation"
    )

    val streakValueStr = currentStreak.toString()
    val fullStreakText = stringResource(id = R.string.current_streak, currentStreak)
    val streakAnnotated = buildAnnotatedString {
        append(fullStreakText)
        val startIndex = fullStreakText.indexOf(streakValueStr)
        if (startIndex >= 0) {
            addStyle(
                style = SpanStyle(
                    color = BlueAccent,
                    fontWeight = FontWeight.ExtraBold
                ),
                start = startIndex,
                end = startIndex + streakValueStr.length
            )
        }
    }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueBGDark),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.greeting),
            color = BlueAccent,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily,
            modifier = Modifier.alpha(alpha)
        )

        Spacer(modifier = Modifier.height(AppDimens.paddingHuge))

        Text(
            text = randomQuote,
            color = BlueAccentSecondary,
            fontSize = 18.sp,
            fontFamily = CustomFontFamily,
            modifier = Modifier.padding(horizontal = AppDimens.paddingExtraLarge)
        )

        Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

        Text(
            text = streakAnnotated,
            color = BlueAccentSecondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily
        )

        Text(
            text = levelAnnotated,
            color = BlueAccentSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = CustomFontFamily
        )
    }
}