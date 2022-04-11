package com.djinc.edumotive.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.utils.WindowSize

@Composable
fun ScreenTitle(
    title: String = "",
    spacerHeight: Int = 12,
    manualPadding: Boolean = false,
    windowSize: WindowSize
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.then(if (manualPadding) Modifier.padding(start = if (windowSize == WindowSize.Compact) 20.dp else 40.dp) else Modifier)
    )
    Spacer(modifier = Modifier.height(spacerHeight.dp))
}
