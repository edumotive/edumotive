package com.djinc.edumotive

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
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import com.djinc.edumotive.utils.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ViewModels>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSize = rememberWindowSizeClass()
            EdumotiveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(windowSize, viewModel)
                }
            }
        }

        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models: List<ContentfulModel> ->
                models.forEach { model ->
                    Log.i("modelAPI", "id " + model.id)
                    Log.i("modelAPI", "1 " + model.modelURL)
                    Log.i("modelAPI", "2 " + model.title)
                    Log.i("modelAPI", "3 " + model.image)
                    Log.i("modelAPI", "4 " + model.description)
                }
        }

        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
            modelGroups: List<ContentfulModelGroup> ->
                modelGroups.forEach { modelGroup ->
                    Log.i("modelAPI", "5 " + modelGroup.title)
                    Log.i("modelAPI", "6 " + modelGroup.models.get(0).title)
                }
        }

        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
            exercises: List<ContentfulExercise> ->
                exercises.forEach { exercise ->
                    Log.i("modelAPI", "7 " + exercise.maxTime)
                    Log.i("modelAPI", "8 " + exercise.steps[1])
                    Log.i("modelAPI", "9 " + exercise.models[0].title)
                }
        }


        Contentful().fetchModelByID(id = "3LAIooNGUr60jfslJderrb", errorCallBack = ::errorCatch) {
            model: ContentfulModel ->
                Log.i("modelAPI", "10 " + model.title)
        }
    }
}
