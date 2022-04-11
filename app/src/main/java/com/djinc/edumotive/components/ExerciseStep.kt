package com.djinc.edumotive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.fonts

@Composable
fun ExerciseStep(exerciseStepName: String = "", stepIndex: Int = 0) {
    Card(
        backgroundColor = Background,
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        Column() {
            Text(
                text = exerciseStepName,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 8.dp, top = 12.dp, end = 8.dp, bottom = 8.dp)
            )
            Box(
                contentAlignment = Alignment.CenterEnd, modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(PinkPrimary)
            ) {
                val negativePadding = 8.dp
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .offset(y = -negativePadding)
                        .requiredHeight(24.dp)
                        .requiredWidth(24.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp))
                        .background(PinkPrimary)
                ) {
                    Text(
                        text = stepIndex.toString(),
                        fontWeight = FontWeight.Medium,
                        fontFamily = fonts,
                        fontSize = 14.sp,
                        color = Background,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}
