package com.djinc.edumotive.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.cards.ExerciseCard
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.screens.Exercise
import com.djinc.edumotive.screens.Part
import com.djinc.edumotive.ui.theme.PinkSecondary

enum class SliderDirection { Horizontal, Vertical }
enum class SliderComponent { PartCard, ExerciseCard }

@ExperimentalFoundationApi
@Composable
fun <T> LazySlider(
    title: String = "",
    direction: SliderDirection,
    list: List<T>,
    component: SliderComponent
) {
    if (title != "") {
        Text(text = title, style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(8.dp))
    }
    when (direction) {
        SliderDirection.Horizontal -> {
            when (component) {
                SliderComponent.PartCard -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(list as List<Part>) { index, item ->
                            PartCard(partName = item.name)
                        }
                    }
                }
                SliderComponent.ExerciseCard -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(list as List<Exercise>) { index, item ->
                            ExerciseCard(
                                exerciseName = item.name,
                                description = item.description,
                                fullWidth = false
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
                        contentPadding = PaddingValues(bottom = 60.dp),
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        itemsIndexed(list as List<Part>) { index, item ->
                            PartCard(partName = item.name)
                        }
                    }
                }
                SliderComponent.ExerciseCard -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 60.dp),
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        itemsIndexed(list as List<Exercise>) { index, item ->
                            ExerciseCard(
                                exerciseName = item.name,
                                description = item.description,
                                fullWidth = true
                            )
                        }
                    }
                }
            }
        }
    }
}