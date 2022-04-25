package com.djinc.edumotive.screens.ar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.screens.gridItems
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.fonts

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PartDrawer(list: List<ContentfulModel>) {
    val verticalLineWidth = 12.dp
    val drawerButtonSize = 50.dp

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(.35f)
                .background(Background)
        ) {
            VerticalLine(lineWidth = verticalLineWidth)
            LazyColumn(
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 28.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Bijbehorende onderdelen",
                        fontFamily = fonts,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                gridItems(
                    data = list,
                    columnCount = 2,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                ) { item ->
                    PartCard(
                        partId = item.id,
                        partType = item.type,
                        partName = item.title,
                        imageUrl = item.image
                    )
                }
            }
            DrawerButton(buttonSize = drawerButtonSize)
        }
    }
}

fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

@Composable
fun VerticalLine(lineWidth: Dp) {
    Box(
        modifier = Modifier
            .offset(x = -lineWidth)
            .fillMaxHeight()
            .width(lineWidth)
            .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
            .background(PinkPrimary)
    )
}

@Composable
fun DrawerButton(buttonSize: Dp) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxHeight()
            .offset(x = -buttonSize)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(buttonSize)
                .height(buttonSize)
                .clip(CircleShape)
                .background(PinkPrimary)
                .clickable { }
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(CircleShape)
                    .background(Background)
                    .padding(6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrows_right),
                    contentDescription = "Minimize drawer",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .rotate(0f)
                )
            }
        }
    }
}