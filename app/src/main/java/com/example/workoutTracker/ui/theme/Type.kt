package com.example.workouttracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R

val CustomFontFamily = FontFamily(
    Font(R.font.oxanium_regular, weight = FontWeight.Normal),
    Font(R.font.oxanium_medium, weight = FontWeight.Medium),
    Font(R.font.oxanium_bold, weight = FontWeight.Bold),
    Font(R.font.oxanium_extra_bold, weight = FontWeight.ExtraBold),
    Font(R.font.oxanium_extra_light, weight = FontWeight.ExtraLight)
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)