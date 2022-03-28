package com.djinc.edumotive.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.ui.theme.Background

@Composable
fun ExerciseStep(exerciseStepName: String = "", stepIndex: Int = 0) {
    Card(
        backgroundColor = Background,
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(text = exerciseStepName)
            Text(text = stepIndex.toString(), style = MaterialTheme.typography.h5)
        }
    }
}