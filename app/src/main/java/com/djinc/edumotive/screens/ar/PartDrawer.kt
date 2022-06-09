package com.djinc.edumotive.screens.ar

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R
import com.djinc.edumotive.components.ExerciseStep
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.ContentfulExerciseAssemble
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelStep
import com.djinc.edumotive.screens.gridItems
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.fonts
import io.github.sceneview.ar.node.ArModelNode
import okhttp3.internal.toImmutableList
import java.util.*
import kotlin.concurrent.schedule

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PartDrawer(
    list: List<ContentfulModel>,
    type: String? = "parts",
    steps: MutableList<ContentfulModelStep>? = mutableListOf(),
    shuffledSteps: MutableList<ContentfulModelStep>? = mutableListOf(),
    currentStep: MutableState<Int>? = mutableStateOf(0),
    callback: (ArModelNode) -> Unit,
    answerCallback: (Boolean) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp + 1
    val windowSize =
        if (screenWidth < 600) WindowSize.Compact else if (screenWidth < 840) WindowSize.Medium else WindowSize.Expanded
    val allowedSpace =
        if (windowSize == WindowSize.Expanded) 0.35f else 0.5f
    val isDrawerOpen = remember { mutableStateOf(steps!!.isNotEmpty()) }
    val drawerSize: Dp by animateDpAsState(if (!isDrawerOpen.value) (screenWidth * allowedSpace).dp else 0.dp)
    val verticalLineWidth = 12.dp
    val drawerButtonSize = 50.dp
    val activePart = remember { mutableStateOf("") }

    if (list.size > 1) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .offset(x = drawerSize)
                    .fillMaxHeight()
                    .fillMaxWidth(allowedSpace)
                    .background(Background)
            ) {
                VerticalLine(lineWidth = verticalLineWidth)
                DrawerButton(buttonSize = drawerButtonSize, drawerState = isDrawerOpen.value) {
                    isDrawerOpen.value = it
                }
                LazyColumn(
                    contentPadding = PaddingValues(
                        vertical = if (windowSize == WindowSize.Compact) 12.dp else 24.dp,
                        horizontal = if (windowSize == WindowSize.Compact) 12.dp else 28.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        DrawerTitle(type = type!!, windowSize = windowSize)
                    }
                    if (steps!!.isNotEmpty()) {
                        shuffledSteps!!.forEach { step ->
                            item {
                                val answerResult = remember { mutableStateOf("") }
                                var stepIndex by remember { mutableStateOf(0) }
                                Box(modifier = Modifier
                                    .clickable {
                                        answerStepExerciseRecognition(
                                            steps = steps,
                                            currentStep = currentStep,
                                            step = step,
                                            type = type!!,
                                            answerCallback = { bool, int ->
                                                answerResult.value =
                                                    if (bool) "correct" else "incorrect"
                                                stepIndex = int
                                                Timer().schedule(800) {
                                                    answerResult.value = ""
                                                }
                                                answerCallback.invoke(bool)
                                            }
                                        )
                                    }
                                    .animateItemPlacement(animationSpec = tween(durationMillis = 600))) {
                                    if (type == ContentfulContentModel.EXERCISEASSEMBLE.stringValue) {
                                        ExerciseStep(
                                            exerciseStepName = step.getModelName(),
                                            stepIndex = if (steps.first()
                                                    .getModelName() == step.getModelName()
                                            ) 1 else stepIndex,
                                            answer = answerResult.value
                                        )
                                    } else {
                                        ExerciseStep(
                                            exerciseStepName = step.getModelName(),
                                            answer = answerResult.value
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        gridItems(
                            data = list,
                            columnCount = if (windowSize == WindowSize.Compact) 1 else 2,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                        ) { item ->
                            PartCard(
                                partId = item.id,
                                partType = item.type,
                                partName = item.title,
                                imageUrl = item.image,
                                activePart = activePart.value
                            ) { partId ->
                                activePart.value = if (activePart.value != partId) partId else ""
                                callback.invoke(item.arModel!!)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun answerStepExerciseRecognition(
    steps: MutableList<ContentfulModelStep>,
    currentStep: MutableState<Int>?,
    step: ContentfulModelStep,
    type: String,
    answerCallback: (Boolean, Int) -> Unit
) {
    if (steps.size - 1 != currentStep!!.value || (type == ContentfulContentModel.EXERCISERECOGNITION.stringValue && steps.size != currentStep.value)) {
        when (type) {
            ContentfulContentModel.EXERCISEASSEMBLE.stringValue -> {
                if (step == steps[currentStep.value + 1]) {
                    answerCallback(true, currentStep.value + 2)
                } else {
                    answerCallback(false, 0)
                }
            }
            ContentfulContentModel.EXERCISEMANUAL.stringValue -> {
                //TODO
            }
            ContentfulContentModel.EXERCISERECOGNITION.stringValue -> {
                if (step == steps[currentStep.value]) {
                    answerCallback(true, 0)
                } else {
                    answerCallback(false, 0)
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

@Composable
fun VerticalLine(lineWidth: Dp) {
    Box(
        modifier = Modifier
            .offset(x = -lineWidth)
            .fillMaxHeight()
            .width(lineWidth)
            .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            .background(PinkPrimary)
    )
}

@Composable
fun DrawerButton(buttonSize: Dp, drawerState: Boolean, callback: (Boolean) -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxHeight()
            .offset(x = -buttonSize)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    if (dragAmount.x < 0) callback(true) else callback(false)
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_drawer_curve_top),
            contentDescription = "Iets",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(27.dp)
                .height(45.dp)
                .offset(x = 12.dp, y = -buttonSize + 8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_drawer_curve_bottom),
            contentDescription = "Iets",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(27.dp)
                .height(45.dp)
                .offset(x = 12.dp, y = buttonSize - 8.dp)

        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(buttonSize)
                .height(buttonSize)
                .clip(CircleShape)
                .background(PinkPrimary)
                .clickable {
                    callback.invoke(!drawerState)
                }
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
                        .rotate(if (drawerState) 0f else 180f)
                )
            }
        }
    }
}

@Composable
fun DrawerTitle(type: String, windowSize: WindowSize) {
    val text: String

    when (type) {
        ContentfulContentModel.EXERCISEASSEMBLE.stringValue -> {
            text = if (windowSize == WindowSize.Compact)
                "Juist volgorde"
            else
                "Plaats in juiste volgorde"
        }
        ContentfulContentModel.EXERCISEMANUAL.stringValue -> {
            text = if (windowSize == WindowSize.Compact)
                "Handleiding oefening"
            else
                "Handleiding oefening"
        }
        ContentfulContentModel.EXERCISERECOGNITION.stringValue -> {
            text = if (windowSize == WindowSize.Compact)
                "Kies het juiste onderdeel"
            else
                "Kies het juiste onderdeel"
        }
        else -> {
            text = if (windowSize == WindowSize.Compact)
                stringResource(R.string.parts_drawer_short)
            else
                stringResource(R.string.parts_drawer_long)
        }
    }
    Text(
        text = text,
        fontFamily = fonts,
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium
    )
}
