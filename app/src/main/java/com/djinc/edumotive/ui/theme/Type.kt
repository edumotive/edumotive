package com.djinc.edumotive.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R

val fonts = FontFamily(
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.poppins_semibold, weight = FontWeight.SemiBold),
    Font(R.font.poppins_bold, weight = FontWeight.Bold)
)

val Typography = Typography(
    // HEADER
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        color = BluePrimary
    ),
    // SUBHEADER
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        color = BluePrimary
    ),
    // CARD TITLE
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = TextPrimary
    ),
    // CARD INFO
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        color = TextPrimary
    ),
    // CARD EXERCISE CHAPTER
    caption = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = TextSecondary
    ),
    // BUTTON
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        color = Background
    ),
    // DETAIL INFO TITLE
    h4 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = TextPrimary
    ),
    // DETAIL INFO
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextSecondary
    ),
    // EXERCISE STEPS
    h5 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextPrimary
    )
)
