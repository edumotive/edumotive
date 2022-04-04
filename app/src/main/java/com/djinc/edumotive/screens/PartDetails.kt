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
import androidx.compose.runtime.*
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
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.screens.ar.ARActivity
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary
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
        if (!isLoading) Details(model = viewModels.activeModel, modelType = modelType, nav = nav, windowSize = windowSize, viewModels = viewModels)
    } else {
        LaunchedEffect(key1 = partId) {
            Contentful().fetchModelGroupById(partId, errorCallBack = ::errorCatch) {
                viewModels.activeModelGroup = it
                isLoading = false
            }
        }
        if (!isLoading) Details(model = viewModels.activeModelGroup, modelType = modelType, nav = nav, windowSize = windowSize, viewModels = viewModels)
    }
}

@Composable
fun Details(
        model: Any,
        modelType: String,
        nav: NavController,
        windowSize: WindowSize,
        viewModels: ViewModels
) {
    val context = LocalContext.current
    val modelURL: String
    val title: String
    val imageUrl: String
    val description: String
    val models: List<ContentfulModel>

    if (modelType == "model") {
        model as ContentfulModel
        modelURL = model.modelURL
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
                ScreenTitle(title = title)
                Box(modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PinkSecondary)
                        .clickable {
                            // SEND MODELURL OR LIST OF MODELURLS ALONG WITH ACTIVITY
                            context.startActivity(Intent(context, ARActivity::class.java))
                        }
                ) {
                    Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                                painter = painterResource(id = R.drawable.ic_augmented_reality),
                                contentDescription = "See in Augmented Reality",
                                tint = PinkPrimary
                        )
                        Text(
                                text = "Open in AR",
                                color = PinkPrimary,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
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
        if (!models.isEmpty()) {
            item {
                ScreenTitle(title = "Bijbehorende onderdelen", spacerHeight = 0)
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
