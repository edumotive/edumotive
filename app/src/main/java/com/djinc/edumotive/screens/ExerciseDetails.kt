package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.*
import com.djinc.edumotive.models.ExerciseStep
import com.djinc.edumotive.ui.theme.TextSecondary
import com.djinc.edumotive.utils.WindowSize

@ExperimentalFoundationApi
@Composable
fun ExerciseDetails(exerciseId: String = "", nav: NavController, windowSize: WindowSize) {
    LazyColumn(
            contentPadding = if (windowSize == WindowSize.Compact) PaddingValues(bottom = 85.dp) else PaddingValues(
                    bottom = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            ScreenTitle(title = "Oefening - Naam", spacerHeight = 0)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Hoofdstuk X", color = TextSecondary, fontSize = 16.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_clock), contentDescription = "Clock icon", tint = TextSecondary, modifier = Modifier.padding(bottom = 3.dp))
                    Text(text = "10 - 15 min", color = TextSecondary, fontSize = 16.sp)
                }
            }
        }
        item {
            Text(text = "Wat je leert in deze oefening", style = MaterialTheme.typography.h4)
            Text(
                    text = "Amet hendrerit amet, donec vulputate auctor imperdiet curabitur sagittis. Integer vitae id a, nunc, vestibulum consectetur nunc, cursus. Nibh vulputate vitae arcu sed ac eu. Massa ultricies sodales sagittis, consequat, egestas lorem sit.",
                    style = MaterialTheme.typography.body2,
            )
        }
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
        itemsIndexed(exerciseSteps) { index, item ->
            ExerciseStep(exerciseStepName = item.name, stepIndex = index + 1)
        }
    }
}
