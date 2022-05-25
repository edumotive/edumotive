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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.AsyncImage
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.ui.theme.fonts

@Composable
fun ExerciseCard(
    exerciseId: String,
    exerciseName: String = "",
    exerciseType: ContentfulContentModel,
    imageUrl: String,
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
                nav.navigate("exercise/$exerciseId/${exerciseType.stringValue}")
            }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CardImage(
                url = imageUrl,
                name = exerciseName,
                aspectRatio = 2f
            )
            ExerciseTitle(text = exerciseName)
            TypeWithButton(type = exerciseType)
        }
    }
}

@Composable
fun ExerciseTitle(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(start = 12.dp, top = 2.dp, end = 12.dp)
    )
}

@Composable
fun TypeWithButton(type: ContentfulContentModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (type == ContentfulContentModel.EXERCISEASSEMBLE) stringResource(id = R.string.exercise_type_assemble) else if (type == ContentfulContentModel.EXERCISEMANUAL) stringResource(
                R.string.exercise_type_manual
            ) else stringResource(R.string.exercise_type_recognition),
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