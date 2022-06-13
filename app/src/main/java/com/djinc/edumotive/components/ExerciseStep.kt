package com.djinc.edumotive.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R
import com.djinc.edumotive.ui.theme.*

@Composable
fun ExerciseStep(exerciseStepName: String = "", stepIndex: Int = 0, answer: String) {
    Card(
        backgroundColor = Background,
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        Column {
            StepName(name = exerciseStepName, answer = answer)
            BottomBar(index = stepIndex)
        }
    }
}

@Composable
fun StepName(name: String, answer: String) {
    val answerIcon: Int = when (answer) {
        "correct" -> R.drawable.ic_answer_correct
        "incorrect" -> R.drawable.ic_answer_incorrect
        else -> 0
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (answerIcon > 0) {
            Image(
                imageVector = ImageVector.vectorResource(id = answerIcon),
                contentDescription = "answer icon",
                modifier = Modifier
                    .requiredWidth(24.dp)
                    .requiredHeight(24.dp)
                    .padding(start = 8.dp)
            )
        }
        Text(
            text = name,
            fontFamily = fonts,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp, top = 12.dp, end = 8.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun BottomBar(index: Int) {
    Box(
        contentAlignment = Alignment.CenterEnd, modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(PinkPrimary)
    ) {
        if (index > 0) {
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
                    text = index.toString(),
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