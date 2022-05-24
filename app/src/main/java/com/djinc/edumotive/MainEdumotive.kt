package com.djinc.edumotive

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.djinc.edumotive.constants.Common
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.models.*
import com.djinc.edumotive.utils.LoadHelper
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import java.time.LocalDate

class MainEdumotive : Application() {
    override fun onCreate() {
        super.onCreate()
        contentfulCachedContent = ContentfulCachedContent()

        appContext = applicationContext

        val context = applicationContext
        var currentLocale = context.resources.configuration.locales[0].toLanguageTag()
        sharedPref = context.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        if (currentLocale !in Common.allLanguages) {
            currentLocale = if (sharedPref!!.getString(
                    getString(R.string.locale),
                    Common.defaultLanguage
                ) !in Common.allLanguages
            ) {
                Common.defaultLanguage
            } else {
                sharedPref!!.getString(getString(R.string.locale), Common.defaultLanguage)!!
            }
        }
        with(sharedPref!!.edit()) {
            putString(getString(R.string.locale), currentLocale)
            apply()
        }

        init()
    }

    private fun init() {
        val loadHelper = LoadHelper(amountNeeded = 6)

        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
            modelGroups = it
            filteredModelGroups = it
            loadedContent(loadHelper)
        }
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            filteredModels = it
            loadedContent(loadHelper)
        }
        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
            exercises = it
            loadedContent(loadHelper)
        }
        Contentful().fetchAllExercisesManual(errorCallBack = ::errorCatch) {
            exercisesManual = it
            loadedContent(loadHelper)
        }
        Contentful().fetchAllExercisesAssemble(errorCallBack = ::errorCatch) {
            exerciseAssemble = it
            loadedContent(loadHelper)
        }
        Contentful().fetchAllExercisesRecognition(errorCallBack = ::errorCatch) {
            exerciseRecognition = it
            loadedContent(loadHelper)
        }

        contentfulCachedContent!!.date = LocalDate.now().toString()
    }


    companion object {
        var appContext: Context? = null
            private set
        var sharedPref: SharedPreferences? = null
            private set
        var contentfulCachedContent: ContentfulCachedContent? = null
            private set
        var modelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
            private set
        var models by mutableStateOf(listOf<ContentfulModel>())
            private set
        var exercises by mutableStateOf(listOf<ContentfulExercise>())
            private set
        var exercisesManual by mutableStateOf(listOf<ContentfulExerciseManual>())
            private set
        var exerciseAssemble by mutableStateOf(listOf<ContentfulExerciseAssemble>())
            private set
        var exerciseRecognition by mutableStateOf(listOf<ContentfulExerciseRecognition>())
            private set

        // FILTERED MODELS & MODELGROUPS
        var filteredModelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
        var filteredModels by mutableStateOf(listOf<ContentfulModel>())

        // LOADING STATES
        var isInitialLoaded by mutableStateOf(false)
            private set

        // LOCALISATION
        var currentLocale by mutableStateOf("")
        var isLanguageModalOpen by mutableStateOf(false)

        fun refresh(
            contentfulContentModels: List<ContentfulContentModel>,
            callback: (result: Boolean) -> Unit
        ) {
            val loadHelper = LoadHelper(amountNeeded = contentfulContentModels.size)

            contentfulContentModels.forEach { entryType ->
                when (entryType) {
                    ContentfulContentModel.MODEL -> {
                        contentfulCachedContent!!.models = emptyList()
                        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                            models = it
                            filteredModels = it
                            loadedContent(loadHelper, callback)
                        }
                    }
                    ContentfulContentModel.MODELGROUP -> {
                        contentfulCachedContent!!.modelGroups = emptyList()
                        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                            modelGroups = it
                            filteredModelGroups = it
                            loadedContent(loadHelper, callback)
                        }
                    }
                    ContentfulContentModel.EXERCISE -> {
                        contentfulCachedContent!!.exercises = emptyList()
                        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
                            exercises = it
                            loadedContent(loadHelper, callback)
                        }
                    }
                    ContentfulContentModel.EXERCISEMANUAL -> {
                        contentfulCachedContent!!.exercisesManual = emptyList()
                        Contentful().fetchAllExercisesManual(errorCallBack = ::errorCatch) {
                            exercisesManual = it
                            loadedContent(loadHelper, callback)
                        }
                    }
                    ContentfulContentModel.EXERCISEASSEMBLE -> {
                        contentfulCachedContent!!.exerciseAssemble = emptyList()
                        Contentful().fetchAllExercisesAssemble(errorCallBack = ::errorCatch) {
                            exerciseAssemble = it
                            loadedContent(loadHelper, callback)
                        }
                    }
                    ContentfulContentModel.EXERCISERECOGNITION -> {
                        contentfulCachedContent!!.exerciseRecognition = emptyList()
                        Contentful().fetchAllExercisesRecognition(errorCallBack = ::errorCatch) {
                            exerciseRecognition = it
                            loadedContent(loadHelper, callback)
                        }
                    }
                }
            }
            contentfulCachedContent!!.locale = currentLocale
        }

        fun refreshAll() {
            Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                models = it
                filteredModels = it
            }
            Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                modelGroups = it
                filteredModelGroups = it
            }
            Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
                exercises = it
            }
            Contentful().fetchAllExercisesManual(errorCallBack = ::errorCatch) {
                exercisesManual = it
            }
            Contentful().fetchAllExercisesAssemble(errorCallBack = ::errorCatch) {
                exerciseAssemble = it
            }
            Contentful().fetchAllExercisesRecognition(errorCallBack = ::errorCatch) {
                exerciseRecognition = it
            }
            contentfulCachedContent!!.locale = currentLocale
        }

        private fun loadedContent(
            loadHelper: LoadHelper,
            callback: ((result: Boolean) -> Unit)? = null
        ) {
            loadHelper.whenLoaded {
                isInitialLoaded = true
                callback?.invoke(true)
            }
        }
    }
}
