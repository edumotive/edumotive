package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.*
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.ui.theme.TextSecondary
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch

@ExperimentalFoundationApi
@Composable
fun ExerciseDetails(
    exerciseId: String = "",
    nav: NavController,
    windowSize: WindowSize,
    viewModels: ViewModels
) {
    LaunchedEffect(key1 = exerciseId) {
        Contentful().fetchExercisesById(exerciseId, errorCallBack = ::errorCatch) {
            viewModels.activeExercise = it
            viewModels.isActiveExerciseLoaded = true
        }
    }
    if (viewModels.isActiveExerciseLoaded) Details(
        exercise = viewModels.activeExercise,
        nav = nav,
        windowSize = windowSize,
        viewModels = viewModels
    )
}

@Composable
fun Details(
    exercise: ContentfulExercise,
    nav: NavController,
    windowSize: WindowSize,
    viewModels: ViewModels
) {
    Box(contentAlignment = Alignment.TopStart, modifier = Modifier.fillMaxWidth(1f)) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth(if (windowSize == WindowSize.Expanded) 0.5f else 1f)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                ScreenTitle(
                    title = exercise.title,
                    spacerHeight = 0,
                    windowSize = windowSize,
                    viewModels = viewModels
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = exercise.chapter, color = TextSecondary, fontSize = 16.sp)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = "Clock icon",
                            tint = TextSecondary,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                        Text(
                            text = "${exercise.minTime} - ${exercise.maxTime} min",
                            color = TextSecondary,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.exercise_info),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = exercise.description,
                    style = MaterialTheme.typography.body2,
                )
            }
            if (windowSize != WindowSize.Expanded) {
                itemsIndexed(exercise.steps) { index, item ->
                    ExerciseStep(exerciseStepName = item, stepIndex = index + 1)
                }
            }
        }
    }
    if (windowSize == WindowSize.Expanded) {
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth(1f)) {
            LazyColumn(
                contentPadding = PaddingValues(end = 40.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 120.dp)
            ) {
                itemsIndexed(exercise.steps) { index, item ->
                    ExerciseStep(exerciseStepName = item, stepIndex = index + 1)
                }
            }
        }
    }
}
