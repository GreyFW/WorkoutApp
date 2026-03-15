package com.example.workouttracker.ui.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.data.prefs.WorkoutPrefs
import com.example.workouttracker.utils.LevelProgressBar
import com.example.workouttracker.ui.theme.*

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val prefs = remember { WorkoutPrefs(context) }

    val quotes = stringArrayResource(id = R.array.splash_quotes)
    val randomQuote = remember { quotes.random() }

    val currentStreak = prefs.streak
    val currentLevel = prefs.level
    val maxLevel = prefs.maxLevel

    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
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
                end = fullStreakText.length
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueBGDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.greeting),
                color = BlueAccent,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingHuge))

            Text(
                text = randomQuote,
                color = BlueAccentSecondary,
                fontSize = 18.sp,
                fontFamily = CustomFontFamily,
                modifier = Modifier.padding(horizontal = AppDimens.paddingExtraLarge)
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingHuge))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimens.paddingExtraLarge),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = streakAnnotated,
                    color = BlueAccentSecondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CustomFontFamily
                )

                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                LevelProgressBar(
                    currentLevel = currentLevel,
                    maxLevel = maxLevel
                )
            }
        }
    }
}