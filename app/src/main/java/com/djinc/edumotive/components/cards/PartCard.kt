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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.AsyncImage
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.utils.WindowSize

@Composable
fun PartCard(
    partId: String,
    partType: String,
    partName: String = "",
    imageUrl: String,
    nav: NavController,
    windowSize: WindowSize
) {
    Card(
        backgroundColor = Background,
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(if (windowSize == WindowSize.Compact) 200.dp else 170.dp)
            .clickable {
                nav.navigate("part/$partId/$partType")
            }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(1.0F)
                    .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(imageUrl = imageUrl, imageName = partName)
            }
            Text(
                text = partName,
                style = MaterialTheme.typography.h3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 12.dp, end = 30.dp)
            )
            Box(
                contentAlignment = Alignment.CenterEnd, modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(PinkPrimary)
            ) {
                val negativePadding = 8.dp
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .offset(y = -negativePadding)
                        .requiredHeight(28.dp)
                        .requiredWidth(28.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp))
                        .background(PinkPrimary)
                        .padding(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrows_right),
                        contentDescription = "See details of part: $partName",
                        tint = Background,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}
