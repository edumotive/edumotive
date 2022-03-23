package com.djinc.edumotive.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenTitle(title: String = "", spacerHeight: Int = 12) {
    Text(text = title, style = MaterialTheme.typography.h1)
    Spacer(modifier = Modifier.height(spacerHeight.dp))
}