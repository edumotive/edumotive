package com.djinc.edumotive.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenTitle(title: String = "", spacerHeight: Int = 12, manualPadding: Boolean = false) {
    Text(
        text = title,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.then(if (manualPadding) Modifier.padding(horizontal = 20.dp) else Modifier)
    )
    Spacer(modifier = Modifier.height(spacerHeight.dp))
}