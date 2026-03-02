package com.example.workouttracker.ui.screens.workout.header

import androidx.compose.material3.Icon
import com.example.workouttracker.R
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.AccentBright
import com.example.workouttracker.ui.theme.AppDimens

@Composable
fun SaveStreakButton(
    isSaved: Boolean,
    onClick: () -> Unit
) {
    val transition = updateTransition(targetState = isSaved, label = "streakSaveTransition")

    val tint by transition.animateColor(
        transitionSpec = { tween(500) },
        label = "streakColor"
    ) { saved ->
        if (saved) AccentBright else BlueAccent
    }

    val scale by transition.animateFloat(
        transitionSpec = { spring(dampingRatio = Spring.DampingRatioMediumBouncy) },
        label = "streakScale"
    ) { saved ->
        if (saved) 1.2f else 1f
    }
    val rotation by transition.animateFloat(
        transitionSpec = { tween(600, easing = FastOutSlowInEasing) },
        label = "streakRotation"
    ) { saved ->
        if (saved) 360f else 0f
    }

    Icon(
        painter = painterResource(id = R.drawable.ic_streak_arrow),
        contentDescription = "Save Workout",
        tint = tint,
        modifier = Modifier
            .size(AppDimens.iconSizeStandard)
            .scale(scale)
            .rotate(rotation)
            .clickable(enabled = !isSaved) { onClick() }
    )
}