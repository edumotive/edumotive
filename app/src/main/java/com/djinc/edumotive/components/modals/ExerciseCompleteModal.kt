package com.djinc.edumotive.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ExerciseCompleteModal() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Background)
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Goed gedaan!")
            Row() {

            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(
                        PinkPrimary
                    )
                    .clickable {
                        // TODO
                    }
            ) {
                Text(
                    "Terug naar oefeningen",
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Background
                )
            }
        }
    }
}