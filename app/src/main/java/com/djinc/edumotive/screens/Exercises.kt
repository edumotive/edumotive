package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection
import com.djinc.edumotive.models.Exercise
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.utils.WindowSize

@ExperimentalFoundationApi
@Composable
fun Exercises(nav: NavController, windowSize: WindowSize, viewModels: ViewModels) {
    Column(modifier = Modifier.padding(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle(title = "Oefeningen", windowSize = windowSize)

        val exercises = listOf(
                Exercise(
                        id = "abcdef",
                        name = "Oefening 1",
                        imageUrl = "https://picsum.photos/seed/edumotive-9/400/200",
                        description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
                ),
                Exercise(
                        id = "abcdefg",
                        name = "Oefening 2",
                        imageUrl = "https://picsum.photos/seed/edumotive-10/400/200",
                        description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
                ),
                Exercise(
                        id = "abcdefgh",
                        name = "Oefening 3",
                        imageUrl = "https://picsum.photos/seed/edumotive-11/400/200",
                        description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
                ),
                Exercise(
                        id = "abcdefghi",
                        name = "Oefening 4",
                        imageUrl = "https://picsum.photos/seed/edumotive-12/400/200",
                        description = "Consectetur amet met da adipiscing maecenas. Daia di consectetur amet met."
                ),
        )
        LazySlider(
                direction = SliderDirection.Vertical,
                list = exercises,
                component = SliderComponent.ExerciseCard,
                nav = nav,
                windowSize = windowSize,
                viewModels = viewModels
        )
    }
}
