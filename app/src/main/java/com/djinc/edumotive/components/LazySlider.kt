package com.djinc.edumotive.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.cards.ExerciseCard
import com.djinc.edumotive.components.cards.PartCard
import com.djinc.edumotive.screens.Exercise
import com.djinc.edumotive.screens.Part

enum class SliderDirection { Horizontal, Vertical }
enum class SliderComponent { PartCard, ExerciseCard }

@Composable
fun <T> LazySlider(
    title: String,
    direction: SliderDirection,
    list: List<T>,
    component: SliderComponent
) {
    Text(text = title, style = MaterialTheme.typography.h2)
    Spacer(modifier = Modifier.height(8.dp))
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
                            ExerciseCard(exerciseName = item.name, description = item.description)
                        }
                    }
                }
            }
        }
        SliderDirection.Vertical -> {
            Text(text = "Vertical")
        }
    }
}