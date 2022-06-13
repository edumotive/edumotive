package com.djinc.edumotive.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.AsyncImage
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import com.djinc.edumotive.screens.ar.ARActivity
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary
import com.djinc.edumotive.ui.theme.fonts
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import java.lang.Exception

@ExperimentalFoundationApi
@Composable
fun PartDetails(
    partId: String = "",
    modelType: ContentfulContentModel,
    nav: NavController,
    windowSize: WindowSize
) {
    val isActiveModelLoaded = remember { mutableStateOf(false) }
    val isLinkedModelGroupLoaded = remember { mutableStateOf(false) }
    val isActiveModelGroupLoaded = remember { mutableStateOf(false) }
    val linkedModelGroup = remember { mutableStateOf(listOf<ContentfulModelGroup>()) }

    if (modelType == ContentfulContentModel.MODEL) {
        val activeModel = remember { mutableStateOf(ContentfulModel()) }
        LaunchedEffect(key1 = partId) {
            isLinkedModelGroupLoaded.value = false
            isActiveModelLoaded.value = false
            try {
                Contentful().fetchLinkedModelGroupById(partId, errorCallBack = ::errorCatch) {
                    linkedModelGroup.value = it
                    isLinkedModelGroupLoaded.value = true
                    activeModel.value = it[0].models.find { model -> model.id == partId }!!
                    isActiveModelLoaded.value = true
                }
            } catch (e: Exception) {
                Contentful().fetchModelByID(partId, errorCallBack = ::errorCatch) {
                    activeModel.value = it
                    isActiveModelLoaded.value = true
                }
            }
        }
        if (isActiveModelLoaded.value) Details(
            model = activeModel.value,
            modelType = modelType,
            modelId = partId,
            nav = nav,
            windowSize = windowSize,
            linkedModelGroup = linkedModelGroup
        )
    } else {
        val activeModelGroup = remember { mutableStateOf(ContentfulModelGroup()) }
        LaunchedEffect(key1 = partId) {
            isActiveModelGroupLoaded.value = false
            Contentful().fetchModelGroupById(partId, errorCallBack = ::errorCatch) {
                activeModelGroup.value = it
                isActiveModelGroupLoaded.value = true
            }
        }
        if (isActiveModelGroupLoaded.value) Details(
            model = activeModelGroup.value,
            modelType = modelType,
            modelId = partId,
            nav = nav,
            windowSize = windowSize,
            linkedModelGroup = linkedModelGroup
        )
    }
}

@Composable
fun Details(
    model: Any,
    modelType: ContentfulContentModel,
    modelId: String,
    nav: NavController,
    windowSize: WindowSize,
    linkedModelGroup: MutableState<List<ContentfulModelGroup>>
) {
    val context = LocalContext.current
    val title: String
    val imageUrl: String
    val description: String
    val models: List<ContentfulModel>

    if (modelType == ContentfulContentModel.MODEL) {
        model as ContentfulModel
        title = model.title
        imageUrl = model.image
        description = model.description
        models =
            if (linkedModelGroup.value.isNotEmpty()) linkedModelGroup.value[0].models.filterNot { lModel -> lModel.id == model.id } else emptyList()
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
                Text(
                    text = stringResource(R.string.information),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                )
            }
            if (windowSize != WindowSize.Expanded && models.isNotEmpty()) {
                item {
                    ScreenTitle(
                        title = stringResource(R.string.corresponding_parts),
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
                        windowSize = windowSize
                    )
                }
            }
            if (windowSize == WindowSize.Compact) {
                item { Spacer(modifier = Modifier.height(120.dp)) }
            } else {
                item { Spacer(modifier = Modifier.height(50.dp)) }
            }
        }
        OpenInArButton(modelId, modelType, context, windowSize)
    }

    if (windowSize == WindowSize.Expanded && models.isNotEmpty()) {
        PartsSplitScreen(models, nav, windowSize)
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
fun OpenInArButton(
    modelId: String,
    modelType: ContentfulContentModel,
    context: Context,
    windowSize: WindowSize
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth(if (windowSize == WindowSize.Expanded) 0.5f else 1f)
            .fillMaxHeight(1f)
            .padding(bottom = if (windowSize == WindowSize.Compact) 80.dp else 16.dp)
    ) {
        ExtendedFloatingActionButton(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = PinkSecondary,
            text = {
                Text(
                    text = stringResource(R.string.open_ar),
                    color = PinkPrimary,
                    fontSize = 16.sp,
                    fontFamily = fonts,
                    modifier = Modifier.padding(top = 3.dp)
                )
            },
            onClick = {
                val intent = Intent(context, ARActivity::class.java)
                val params = Bundle()
                params.putString("type", modelType.stringValue)
                params.putString("id", modelId)
                intent.putExtras(params)
                context.startActivity(intent)
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_augmented_reality),
                    contentDescription = stringResource(R.string.see_in_augmented_reality),
                    tint = PinkPrimary
                )
            },
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .height(40.dp)
        )
    }
}

@Composable
fun PartsSplitScreen(
    models: List<ContentfulModel>,
    nav: NavController,
    windowSize: WindowSize
) {
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
                    title = stringResource(R.string.corresponding_parts),
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
                        windowSize = windowSize
                    )
                }
            }
        }
    }
}
