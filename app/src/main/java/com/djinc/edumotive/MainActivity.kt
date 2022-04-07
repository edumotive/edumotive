package com.djinc.edumotive

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.screens.App
import com.djinc.edumotive.ui.theme.EdumotiveTheme
import com.djinc.edumotive.utils.WindowSize
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import com.djinc.edumotive.utils.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ViewModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSize = rememberWindowSizeClass()
            if (windowSize != WindowSize.Compact) requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            EdumotiveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (viewModel.isInitialLoaded) App(windowSize, viewModel)
                }
            }
        }

        Contentful().fetchAllModels(errorCallBack = ::errorCatch) { models: List<ContentfulModel> ->
            models.forEach { model ->
                Log.i("modelAPI", "Model: id = ${model.id}")
                Log.i("modelAPI", "Model: type = ${model.type}")
                Log.i("modelAPI", "Model: title = ${model.title}")
                Log.i("modelAPI", "Model: imageURL = ${model.image}")
                Log.i("modelAPI", "Model: info = ${model.description}")
                Log.i("modelAPI", "Model: ModelURL = ${model.modelUrl}")
            }
        }

        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) { modelGroups: List<ContentfulModelGroup> ->
            modelGroups.forEach { modelGroup ->
                Log.i("modelAPI", "ModelGroup: id = ${modelGroup.id}")
                Log.i("modelAPI", "ModelGroup: type = ${modelGroup.type}")
                Log.i("modelAPI", "ModelGroup: title = ${modelGroup.title}")
                Log.i("modelAPI", "ModelGroup: imageURL = ${modelGroup.image}")
                Log.i("modelAPI", "ModelGroup: info = ${modelGroup.description}")
                Log.i("modelAPI", "ModelGroup: models = ${modelGroup.models}")
                Log.i("modelAPI", "ModelGroup: modelUrl = ${modelGroup.modelUrl}")
            }
        }

        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) { exercises: List<ContentfulExercise> ->
            exercises.forEach { exercise ->
                Log.i("modelAPI", "8 " + exercise.maxTime)
                Log.i("modelAPI", "9 " + exercise.steps[1])
                Log.i("modelAPI", "10 " + exercise.models[0].title)
            }
        }
    }
}
