package com.djinc.edumotive.screens

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.ExerciseStep
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.*
import com.djinc.edumotive.screens.ar.ARActivity
import com.djinc.edumotive.ui.theme.*
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch

@ExperimentalFoundationApi
@Composable
fun ExerciseDetails(
    exerciseId: String = "",
    exerciseType: ContentfulContentModel,
    nav: NavController,
    windowSize: WindowSize
) {
    val isActiveExerciseLoaded = remember { mutableStateOf(false) }

    when (exerciseType) {
        ContentfulContentModel.EXERCISEASSEMBLE -> {
            val activeExercise = remember { mutableStateOf(ContentfulExerciseAssemble()) }
            LaunchedEffect(key1 = exerciseId) {
                isActiveExerciseLoaded.value = false
                Contentful().fetchExercisesAssembleById(
                    exerciseId,
                    errorCallBack = ::errorCatch
                ) {
                    activeExercise.value = it
                    isActiveExerciseLoaded.value = true
                }
            }
            if (isActiveExerciseLoaded.value) Details(
                exercise = activeExercise.value,
                exerciseType = exerciseType,
                exerciseId = exerciseId,
                nav = nav,
                windowSize = windowSize
            )
        }
        ContentfulContentModel.EXERCISEMANUAL -> {
            val activeExercise = remember { mutableStateOf(ContentfulExerciseManual()) }
            LaunchedEffect(key1 = exerciseId) {
                isActiveExerciseLoaded.value = false
                Contentful().fetchExercisesManualById(exerciseId, errorCallBack = ::errorCatch) {
                    activeExercise.value = it
                    isActiveExerciseLoaded.value = true
                }
            }
            if (isActiveExerciseLoaded.value) Details(
                exercise = activeExercise.value,
                exerciseType = exerciseType,
                exerciseId = exerciseId,
                nav = nav,
                windowSize = windowSize
            )
        }
        ContentfulContentModel.EXERCISERECOGNITION -> {
            val activeExercise = remember { mutableStateOf(ContentfulExerciseRecognition()) }
            LaunchedEffect(key1 = exerciseId) {
                isActiveExerciseLoaded.value = false
                Contentful().fetchExercisesRecognitionById(
                    exerciseId,
                    errorCallBack = ::errorCatch
                ) {
                    activeExercise.value = it
                    isActiveExerciseLoaded.value = true
                }
            }
            if (isActiveExerciseLoaded.value) Details(
                exercise = activeExercise.value,
                exerciseType = exerciseType,
                exerciseId = exerciseId,
                nav = nav,
                windowSize = windowSize
            )
        }
        else -> {}
    }
}

@Composable
fun Details(
    exercise: Any,
    exerciseType: ContentfulContentModel,
    exerciseId: String,
    nav: NavController,
    windowSize: WindowSize
) {
    val context = LocalContext.current
    val title: String
    val minTime: Int
    val maxTime: Int
    val typeTitle: String
    val typeInfo: String
    val info: String
    val modelsteps: List<ContentfulModelStep>

    when (exerciseType) {
        ContentfulContentModel.EXERCISEASSEMBLE -> {
            exercise as ContentfulExerciseAssemble
            title = exercise.title
            minTime = exercise.minTime
            maxTime = exercise.maxTime
            typeTitle =
                "${stringResource(id = R.string.exercise_type_assemble)} ${stringResource(id = R.string.exercise).lowercase()}"
            typeInfo = stringResource(id = R.string.exercise_type_assemble_info)
            info = exercise.info
            modelsteps = exercise.steps
        }
        ContentfulContentModel.EXERCISEMANUAL -> {
            exercise as ContentfulExerciseManual
            title = exercise.title
            minTime = exercise.minTime
            maxTime = exercise.maxTime
            typeTitle =
                "${stringResource(id = R.string.exercise_type_manual)} ${stringResource(id = R.string.exercise).lowercase()}"
            typeInfo = stringResource(id = R.string.exercise_type_manual_info)
            info = exercise.info
            modelsteps = exercise.steps
        }
        ContentfulContentModel.EXERCISERECOGNITION -> {
            exercise as ContentfulExerciseRecognition
            title = exercise.title
            minTime = exercise.minTime
            maxTime = exercise.maxTime
            typeTitle =
                "${stringResource(id = R.string.exercise_type_recognition)} ${stringResource(id = R.string.exercise).lowercase()}"
            typeInfo = stringResource(id = R.string.exercise_type_recognition_info)
            info = exercise.info
            modelsteps = exercise.steps
        }
        else -> {
            title = ""
            minTime = 0
            maxTime = 0
            typeTitle = ""
            typeInfo = ""
            info = ""
            modelsteps = emptyList()
        }
    }

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
                    title = title,
                    spacerHeight = 0,
                    windowSize = windowSize
                )
                Time(minTime = minTime, maxTime = maxTime)
            }
            item {
                Text(
                    text = typeTitle,
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = typeInfo,
                    style = MaterialTheme.typography.body2,
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.exercise_info),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = info,
                    style = MaterialTheme.typography.body2,
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.exercise_start_title),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = stringResource(id = R.string.exercise_start_both),
                    style = MaterialTheme.typography.body2,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PinkPrimary
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.exercise_start_btn_with_ar),
                            color = Background,
                            fontSize = 16.sp,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PinkSecondary
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.exercise_start_btn_without_ar),
                            color = PinkPrimary,
                            fontSize = 16.sp,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                    }
                }
            }
//            if (windowSize != WindowSize.Expanded) {
//
//            }
        }
    }
    if (windowSize == WindowSize.Expanded) {
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth(1f)) {
            // Add here images of models
        }
    }
}

@Composable
fun Time(minTime: Int, maxTime: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
                text = "$minTime - $maxTime min",
                color = TextSecondary,
                fontSize = 16.sp
            )
        }
    }
}
