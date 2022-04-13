package com.djinc.edumotive.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.AsyncImage
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.constants.WindowSize

@Composable
fun ExerciseCard(
    exerciseId: String,
    exerciseName: String = "",
    chapter: String = "",
    imageUrl: String,
    description: String = "",
    fullWidth: Boolean,
    nav: NavController,
    windowSize: WindowSize
) {
    Card(
        backgroundColor = Background,
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .then(if (fullWidth) Modifier.fillMaxWidth(1f) else Modifier.width(if (windowSize == WindowSize.Compact) 260.dp else 240.dp))
            .clickable {
                nav.navigate("exercise/$exerciseId")
            }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            CardImage(
                url = imageUrl,
                name = exerciseName,
                aspectRatio = 2f
            )
            ExerciseDescription(text = description)
            ChapterWithButton(chapter = chapter)
        }
    }
}

@Composable
fun ExerciseDescription(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    )
}

@Composable
fun ChapterWithButton(chapter: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = chapter,
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
            Text(
                text = stringResource(R.string.more_info),
                style = MaterialTheme.typography.button
            )
        }
    }
}