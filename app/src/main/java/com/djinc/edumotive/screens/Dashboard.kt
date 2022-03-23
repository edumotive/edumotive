package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection
import com.djinc.edumotive.components.cards.PartCard

data class Part(val name: String)
data class Exercise(val name: String, val description: String)

@ExperimentalFoundationApi
@Composable
fun Dashboard() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 32.dp)) {
        ScreenTitle("Welkom!")

        val parts = listOf(
            Part(name = "Onderdeel 1"),
            Part(name = "Onderdeel 2"),
            Part(name = "Onderdeel 3"),
            Part(name = "Onderdeel 4"),
        )
        LazySlider(
            title = "Recent bekeken onderdelen",
            direction = SliderDirection.Horizontal,
            list = parts,
            component = SliderComponent.PartCard
        )

        Spacer(modifier = Modifier.height(36.dp))

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
            title = "Oefeningen voor dit hoofdstuk",
            direction = SliderDirection.Horizontal,
            list = exercises,
            component = SliderComponent.ExerciseCard
        )
    }
}