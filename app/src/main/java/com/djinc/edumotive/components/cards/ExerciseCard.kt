package com.djinc.edumotive.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.components.AsyncImage
import com.djinc.edumotive.navigation.Screen
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary

@Composable
fun ExerciseCard(
    exerciseId: String,
    exerciseName: String = "",
    imageUrl: String,
    description: String = "",
    fullWidth: Boolean,
    nav: NavController
) {
    Card(
        backgroundColor = Background,
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .then(if (fullWidth) Modifier.fillMaxWidth(1f) else Modifier.width(260.dp))
            .clickable {
                nav.navigate("exercise/$exerciseId")
            }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(2F)
                    .background(PinkPrimary)
            ) {
                AsyncImage(imageUrl = imageUrl, imageName = exerciseName)
            }
            Text(
                text = description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exerciseName,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 8.dp))
                        .background(PinkPrimary)
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(text = "Meer info", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}
