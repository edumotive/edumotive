package com.djinc.edumotive.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.components.cards.ExerciseCard
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.models.Exercise
import com.djinc.edumotive.models.ExerciseStep
import com.djinc.edumotive.models.Part

enum class SliderDirection { Horizontal, Vertical }
enum class SliderComponent { PartCard, ExerciseCard, ExerciseStep }

@ExperimentalFoundationApi
@Composable
fun <T> LazySlider(
    title: String = "",
    titleManualPadding: Boolean = false,
    lastElementOnPage: Boolean = false,
    direction: SliderDirection,
    list: List<T>,
    component: SliderComponent,
    nav: NavController
) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.then(if (titleManualPadding) Modifier.padding(horizontal = 20.dp) else Modifier)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    when (direction) {
        SliderDirection.Horizontal -> {
            when (component) {
                SliderComponent.PartCard -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        modifier = Modifier.then(if (lastElementOnPage) Modifier.padding(bottom = 60.dp) else Modifier)
                    ) {
                        itemsIndexed(list as List<Part>) { index, item ->
                            PartCard(
                                partId = item.id,
                                partName = item.name,
                                imageUrl = item.imageUrl,
                                nav = nav
                            )
                        }
                    }
                }
                SliderComponent.ExerciseCard -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        modifier = Modifier.then(if (lastElementOnPage) Modifier.padding(bottom = 60.dp) else Modifier)
                    ) {
                        itemsIndexed(list as List<Exercise>) { index, item ->
                            ExerciseCard(
                                exerciseId = item.id,
                                exerciseName = item.name,
                                description = item.description,
                                fullWidth = false,
                                nav = nav
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
                        cells = GridCells.Adaptive(128.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        modifier = Modifier
                            .fillMaxWidth(1f)
                    ) {
                        itemsIndexed(list as List<Part>) { index, item ->
                            PartCard(
                                partId = item.id,
                                partName = item.name,
                                imageUrl = item.imageUrl,
                                nav = nav
                            )
                        }
                    }
                }
                SliderComponent.ExerciseCard -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(1f)
                    ) {
                        itemsIndexed(list as List<Exercise>) { index, item ->
                            ExerciseCard(
                                exerciseId = item.id,
                                exerciseName = item.name,
                                description = item.description,
                                fullWidth = true,
                                nav = nav
                            )
                        }
                    }
                }
                SliderComponent.ExerciseStep -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        itemsIndexed(list as List<ExerciseStep>) { index, item ->
                            ExerciseStep(exerciseStepName = item.name, stepIndex = index)
                        }
                    }
                }
            }
        }
    }
}
