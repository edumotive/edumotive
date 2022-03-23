package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection

@ExperimentalFoundationApi
@Composable
fun Exercises() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 32.dp)) {
        ScreenTitle("Oefeningen")

        val exercises = listOf(
            Exercise(
                name = "Oefening 1",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
            Exercise(
                name = "Oefening 2",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
            Exercise(
                name = "Oefening 3",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
            Exercise(
                name = "Oefening 4",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
        )
        LazySlider(
            direction = SliderDirection.Vertical,
            list = exercises,
            component = SliderComponent.ExerciseCard
        )
    }
}