package com.djinc.edumotive.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.components.cards.ExerciseCard
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.utils.WindowSize

enum class SliderDirection { Horizontal, Vertical }
enum class SliderComponent { PartCard, ExerciseCard }

@ExperimentalFoundationApi
@Composable
fun <T> LazySlider(
    title: String = "",
    titleManualPadding: Boolean = false,
    direction: SliderDirection,
    list: List<T>,
    list2: List<T> = emptyList(),
    component: SliderComponent,
    nav: NavController,
    windowSize: WindowSize,
    viewModels: ViewModels
) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.then(if (titleManualPadding) Modifier.padding(start = if (windowSize == WindowSize.Compact) 20.dp else 40.dp) else Modifier)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    if (viewModels.isInitialLoaded) {
        when (direction) {
            SliderDirection.Horizontal -> {
                when (component) {
                    SliderComponent.PartCard -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp),
                        ) {
                            itemsIndexed(list.filterIsInstance<ContentfulModel>()) { _, item ->
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
                    SliderComponent.ExerciseCard -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp),
                        ) {
                            itemsIndexed(list.filterIsInstance<ContentfulExercise>()) { _, item ->
                                ExerciseCard(
                                    exerciseId = item.id,
                                    exerciseName = item.title,
                                    imageUrl = item.image,
                                    description = item.description,
                                    fullWidth = false,
                                    nav = nav,
                                    windowSize = windowSize
                                )
                            }
                        }
                    }
                }
            }
            SliderDirection.Vertical -> {
                when (component) {
                    SliderComponent.PartCard -> {
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(if (windowSize == WindowSize.Compact) 128.dp else 180.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = if (windowSize == WindowSize.Compact) PaddingValues(
                                bottom = 100.dp
                            ) else PaddingValues(
                                bottom = 24.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            itemsIndexed(list.filterIsInstance<ContentfulModelGroup>()) { _, item ->
                                PartCard(
                                    partId = item.id,
                                    partType = item.type,
                                    partName = item.title,
                                    imageUrl = item.image,
                                    nav = nav,
                                    windowSize = windowSize
                                )
                            }
                            itemsIndexed(list2.filterIsInstance<ContentfulModel>()) { _, item ->
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
                    SliderComponent.ExerciseCard -> {
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(if (windowSize == WindowSize.Compact) 200.dp else 250.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = if (windowSize == WindowSize.Compact) PaddingValues(
                                bottom = 100.dp
                            ) else PaddingValues(
                                bottom = 24.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            itemsIndexed(list.filterIsInstance<ContentfulExercise>()) { _, item ->
                                ExerciseCard(
                                    exerciseId = item.id,
                                    exerciseName = item.title,
                                    imageUrl = item.image,
                                    description = item.description,
                                    fullWidth = true,
                                    nav = nav,
                                    windowSize = windowSize
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator()
        }
    }
}
