package com.example.secondapp

import androidx.compose.ui.graphics.Color

data class Feature(
    val title: String,
    val iconId: Int,
    val darkColor: Color,
    val mediumColor: Color,
    val lightColor: Color,
    val soundResId: Int
)

