package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection

data class Part(val name: String)
data class Exercise(val name: String, val description: String)

@ExperimentalFoundationApi
@Composable
fun Dashboard() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle("Welkom!", manualPadding = true)

        val parts = listOf(
            Part(name = "Onderdeel 1"),
            Part(name = "Onderdeel 2"),
            Part(name = "Onderdeel 3"),
            Part(name = "Onderdeel 4"),
        )
        LazySlider(
            title = "Recent bekeken onderdelen",
            titleManualPadding = true,
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
            titleManualPadding = true,
            direction = SliderDirection.Horizontal,
            list = exercises,
            component = SliderComponent.ExerciseCard
        )
    }
}