package com.example.workouttracker

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.workouttracker.ui.screens.splash.SplashScreen
import com.example.workouttracker.ui.screens.workout.WorkoutScreen
import kotlinx.coroutines.delay

enum class AppScreen {
    Splash,
    Workout
}

@Composable
fun WorkoutApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.Splash) }

    LaunchedEffect(Unit) {
        delay(2000)
        currentScreen = AppScreen.Workout
    }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            fadeIn(animationSpec = tween(800)) togetherWith fadeOut(animationSpec = tween(800))
        },
        label = "screen_transition"
    ) { targetScreen ->
        when (targetScreen) {
            AppScreen.Splash -> SplashScreen()
            AppScreen.Workout -> WorkoutScreen()
        }
    }
}