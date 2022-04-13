package com.djinc.edumotive.components.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.AsyncImage

@Composable
fun CardImage(
    url: String,
    name: String,
    aspectRatio: Float,
    padding: Boolean = false,
    rounded: Boolean = false
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(1f)
            .aspectRatio(aspectRatio)
            .then(
                if (padding) Modifier.padding(
                    start = 12.dp,
                    top = 12.dp,
                    end = 12.dp
                ) else Modifier
            )
            .then(if (rounded) Modifier.clip(RoundedCornerShape(8.dp)) else Modifier)
    ) {
        AsyncImage(imageUrl = url, imageName = name)
    }
}