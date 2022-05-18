package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R
import com.djinc.edumotive.components.ExerciseStep
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.ui.theme.TextSecondary
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch

@ExperimentalFoundationApi
@Composable
fun ExerciseDetails(
    exerciseId: String = "",
    windowSize: WindowSize
) {
    val activeExercise = remember { mutableStateOf(ContentfulExercise()) }
    val isActiveExerciseLoaded = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = exerciseId) {
        isActiveExerciseLoaded.value = false
        Contentful().fetchExercisesById(exerciseId, errorCallBack = ::errorCatch) {
            activeExercise.value = it
            isActiveExerciseLoaded.value = true
        }
    }
    if (isActiveExerciseLoaded.value) Details(
        exercise = activeExercise.value,
        windowSize = windowSize
    )
}

@Composable
fun Details(
    exercise: ContentfulExercise,
    windowSize: WindowSize
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
                    windowSize = windowSize
                )
                ChapterWithTime(exercise = exercise)
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
                generateSteps(exercise = exercise)
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
                generateSteps(exercise = exercise)
            }
        }
    }
}

fun LazyListScope.generateSteps(exercise: ContentfulExercise) {
    itemsIndexed(exercise.steps) { index, item ->
        ExerciseStep(exerciseStepName = item, stepIndex = index + 1)
    }
}

@Composable
fun ChapterWithTime(exercise: ContentfulExercise) {
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
