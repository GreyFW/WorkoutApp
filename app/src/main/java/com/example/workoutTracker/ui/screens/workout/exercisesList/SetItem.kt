package com.example.workouttracker.ui.screens.workout.exercisesList

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.ui.theme.BlueAccent
import com.example.workouttracker.ui.theme.BlueAccentSecondary
import com.example.workouttracker.ui.theme.BlueBGDark
import com.example.workouttracker.ui.theme.CustomFontFamily

@Composable
fun SetItem(
    reps: String,
    isCompleted: Boolean,
    onToggle: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isCompleted) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "setScale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isCompleted) BlueAccent else BlueBGDark,
        label = "setColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isCompleted) Color.White else BlueAccent,
        label = "setTextColor"
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .scale(scale)
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = BlueAccentSecondary, shape = RoundedCornerShape(8.dp))
            .clickable { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = reps,
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily
        )
    }
}