package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection
import com.djinc.edumotive.models.Exercise
import com.djinc.edumotive.models.Part

@ExperimentalFoundationApi
@Composable
fun Dashboard(nav: NavController) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle("Welkom!", manualPadding = true)

        val parts = listOf(
            Part(id = "abcdef", name = "Onderdeel 1"),
            Part(id = "abcdefg", name = "Onderdeel 2"),
            Part(id = "abcdefgh", name = "Onderdeel 3"),
            Part(id = "abcdefghi", name = "Onderdeel 4"),
        )
        LazySlider(
            title = "Recent bekeken onderdelen",
            titleManualPadding = true,
            direction = SliderDirection.Horizontal,
            list = parts,
            component = SliderComponent.PartCard,
            nav = nav
        )

        Spacer(modifier = Modifier.height(36.dp))

        val exercises = listOf(
            Exercise(
                id = "abcdef",
                name = "Oefening 1",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
            Exercise(
                id = "abcdefg",
                name = "Oefening 2",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
            Exercise(
                id = "abcdefgh",
                name = "Oefening 3",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
            Exercise(
                id = "abcdefghi",
                name = "Oefening 4",
                description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
            ),
        )
        LazySlider(
            title = "Oefeningen voor dit hoofdstuk",
            titleManualPadding = true,
            lastElementOnPage = true,
            direction = SliderDirection.Horizontal,
            list = exercises,
            component = SliderComponent.ExerciseCard,
            nav = nav
        )
    }
}