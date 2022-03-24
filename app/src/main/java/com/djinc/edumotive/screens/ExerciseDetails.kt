package com.djinc.edumotive.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.ScreenTitle

@Composable
fun ExerciseDetails(exerciseId: String = "") {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle(title = exerciseId)
    }
}