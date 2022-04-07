package com.djinc.edumotive.screens

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.*
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.screens.ar.ARActivity
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary
import com.djinc.edumotive.ui.theme.fonts
import com.djinc.edumotive.utils.WindowSize
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch

@ExperimentalFoundationApi
@Composable
fun PartDetails(
    partId: String = "",
    modelType: String,
    nav: NavController,
    windowSize: WindowSize,
    viewModels: ViewModels
) {
    var isLoading by remember { mutableStateOf(true) }
    if (modelType == "model") {
        LaunchedEffect(key1 = partId) {
            Contentful().fetchModelByID(partId, errorCallBack = ::errorCatch) {
                viewModels.activeModel = it
                isLoading = false
            }
        }
        if (!isLoading) Details(
            model = viewModels.activeModel,
            modelType = modelType,
            modelId = partId,
            nav = nav,
            windowSize = windowSize,
            viewModels = viewModels
        )
    } else {
        LaunchedEffect(key1 = partId) {
            Contentful().fetchModelGroupById(partId, errorCallBack = ::errorCatch) {
                viewModels.activeModelGroup = it
                isLoading = false
            }
        }
        if (!isLoading) Details(
            model = viewModels.activeModelGroup,
            modelType = modelType,
            modelId = partId,
            nav = nav,
            windowSize = windowSize,
            viewModels = viewModels
        )
    }
}

@Composable
fun Details(
    model: Any,
    modelType: String,
    modelId: String,
    nav: NavController,
    windowSize: WindowSize,
    viewModels: ViewModels
) {
    val context = LocalContext.current
    val title: String
    val imageUrl: String
    val description: String
    val models: List<ContentfulModel>

    if (modelType == "model") {
        model as ContentfulModel
        title = model.title
        imageUrl = model.image
        description = model.description
        models = emptyList()
    } else {
        model as ContentfulModelGroup
        title = model.title
        imageUrl = model.image
        description = model.description
        models = model.models
    }

    Box(contentAlignment = Alignment.TopStart, modifier = Modifier.fillMaxWidth(1f)) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth(if (windowSize == WindowSize.Expanded) 0.5f else 1f)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                ScreenTitle(title = title, windowSize = windowSize)
            }
            item {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxWidth(1f)
                        .aspectRatio(1F)
                        .background(Background, RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(imageUrl = imageUrl, imageName = "TODO")
                }
            }
            item {
                Text(text = "Informatie", style = MaterialTheme.typography.h4)
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                )
            }
            if (windowSize != WindowSize.Expanded && models.isNotEmpty()) {
                item {
                    ScreenTitle(
                        title = "Bijbehorende onderdelen",
                        spacerHeight = 0,
                        windowSize = windowSize
                    )
                }
                gridItems(
                    data = models,
                    columnCount = 2,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                ) { item ->
                    PartCard(
                        partId = item.id,
                        partType = item.type,
                        partName = item.title,
                        imageUrl = item.image,
                        nav = nav,
                        viewModels = viewModels
                    )
                }
            }
            if (windowSize == WindowSize.Compact) {
                item { Spacer(modifier = Modifier.height(90.dp)) }
            } else {
                item { Spacer(modifier = Modifier.height(50.dp)) }
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth(if (windowSize == WindowSize.Expanded) 0.5f else 1f)
                .fillMaxHeight(1f)
                .padding(bottom = 16.dp)
        ) {
            ExtendedFloatingActionButton(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = PinkSecondary,
                text = {
                    Text(
                        text = "Open in AR",
                        color = PinkPrimary,
                        fontSize = 16.sp,
                        fontFamily = fonts,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                },
                onClick = {
                    val intent = Intent(context, ARActivity::class.java)
                    val params = Bundle()
                    params.putString("type", modelType)
                    params.putString("id", modelId)
                    intent.putExtras(params)
                    context.startActivity(intent)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_augmented_reality),
                        contentDescription = "See in Augmented Reality",
                        tint = PinkPrimary
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .height(40.dp)
            )
        }

    }

    if (windowSize == WindowSize.Expanded) {
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth(1f)) {
            LazyColumn(
                contentPadding = PaddingValues(end = 40.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
                item {
                    ScreenTitle(
                        title = "Bijbehorende onderdelen",
                        spacerHeight = 0,
                        windowSize = windowSize
                    )
                }
                if (models.isNotEmpty()) {
                    gridItems(
                        data = models,
                        columnCount = 2,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                    ) { item ->
                        PartCard(
                            partId = item.id,
                            partType = item.type,
                            partName = item.title,
                            imageUrl = item.image,
                            nav = nav,
                            viewModels = viewModels
                        )
                    }
                }
            }
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
