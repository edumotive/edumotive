package com.djinc.edumotive.screens

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.*
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.models.Part
import com.djinc.edumotive.screens.ar.ARActivity
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary
import com.djinc.edumotive.utils.WindowSize

@ExperimentalFoundationApi
@Composable
fun PartDetails(partId: String = "", nav: NavController, windowSize: WindowSize) {
    val context = LocalContext.current
    // GET DATA FROM PART BASED ON GIVEN ID
    val imageUrl = "https://picsum.photos/seed/edumotive-16/400"

    LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(1f)
            ) {
                ScreenTitle(title = "Onderdeel")
                Box(modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PinkSecondary)
                        .clickable {
                            context.startActivity(Intent(context, ARActivity::class.java))
                        }
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Icon(painter = painterResource(id = R.drawable.ic_augmented_reality), contentDescription = "See $partId in Augmented Reality", tint = PinkPrimary)
                        Text(text = "Open in AR", color = PinkPrimary, fontSize = 15.sp, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }
        item {
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(1F)
                    .background(PinkSecondary, RoundedCornerShape(8.dp))
            ) {
                AsyncImage(imageUrl = imageUrl, imageName = "TODO")
            }
        }
        item {
            Text(text = "Informatie", style = MaterialTheme.typography.h4)
            Text(
                    text = "Amet hendrerit amet, donec vulputate auctor imperdiet curabitur sagittis. Integer vitae id a, nunc, vestibulum consectetur nunc, cursus. Nibh vulputate vitae arcu sed ac eu. Massa ultricies sodales sagittis, consequat, egestas lorem sit.",
                    style = MaterialTheme.typography.body2,
            )
        }
        item {
            ScreenTitle(title = "Bijbehorende onderdelen", spacerHeight = 0)
        }
        val parts = listOf(
                Part(
                        id = "abcdef",
                        name = "Onderdeel 1",
                        imageUrl = "https://picsum.photos/seed/edumotive-1/400"
                ),
                Part(
                        id = "abcdefg",
                        name = "Onderdeel 2",
                        imageUrl = "https://picsum.photos/seed/edumotive-2/400"
                ),
                Part(
                        id = "abcdefgh",
                        name = "Onderdeel 3",
                        imageUrl = "https://picsum.photos/seed/edumotive-3/400"
                ),
                Part(
                        id = "abcdefghi",
                        name = "Onderdeel 4",
                        imageUrl = "https://picsum.photos/seed/edumotive-4/400"
                ),
                Part(
                        id = "abcdefghij",
                        name = "Onderdeel 5",
                        imageUrl = "https://picsum.photos/seed/edumotive-5/400"
                ),
                Part(
                        id = "abcdefghijk",
                        name = "Onderdeel 6",
                        imageUrl = "https://picsum.photos/seed/edumotive-6/400"
                ),
                Part(
                        id = "abcdefghijkl",
                        name = "Onderdeel 7",
                        imageUrl = "https://picsum.photos/seed/edumotive-7/400"
                ),
                Part(
                        id = "abcdefghijklm",
                        name = "Onderdeel 8",
                        imageUrl = "https://picsum.photos/seed/edumotive-8/400"
                ),
        )
        gridItems(
                data = parts,
                columnCount = 2,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
        ) { item ->
            PartCard(partId = item.id, partName = item.name, imageUrl = item.imageUrl, nav = nav)
        }
        if (windowSize == WindowSize.Compact) {
            item { Spacer(modifier = Modifier.height(65.dp)) }
        } else {
            item { Spacer(modifier = Modifier.height(24.dp)) }
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
