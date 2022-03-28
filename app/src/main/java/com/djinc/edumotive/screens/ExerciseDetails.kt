package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection
import com.djinc.edumotive.models.ExerciseStep
import com.djinc.edumotive.ui.theme.TextSecondary

@ExperimentalFoundationApi
@Composable
fun ExerciseDetails(exerciseId: String = "", nav: NavController) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        ScreenTitle(title = "Oefening - Naam", spacerHeight = 0)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Hoofdstuk X", color = TextSecondary, fontSize = 16.sp)
            Text(text = "O 10 - 15 min", color = TextSecondary, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Wat je leert in deze oefening", style = MaterialTheme.typography.h4)
        Text(
            text = "Amet hendrerit amet, donec vulputate auctor imperdiet curabitur sagittis. Integer vitae id a, nunc, vestibulum consectetur nunc, cursus. Nibh vulputate vitae arcu sed ac eu. Massa ultricies sodales sagittis, consequat, egestas lorem sit.",
            style = MaterialTheme.typography.body2,
        )

        Spacer(modifier = Modifier.height(20.dp))

        val exerciseSteps = listOf(
            ExerciseStep(
                name = "Stap 1",
            ),
            ExerciseStep(
                name = "Stap 2",
            ),
            ExerciseStep(
                name = "Stap 3",
            ),
            ExerciseStep(
                name = "Stap 4",
            ),
            ExerciseStep(
                name = "Stap 5",
            ),
        )
        LazySlider(
            direction = SliderDirection.Vertical,
            lastElementOnPage = true,
            list = exerciseSteps,
            component = SliderComponent.ExerciseStep,
            nav = nav
        )
    }
}
