package com.djinc.edumotive

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.djinc.edumotive.constants.Common
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.models.ContentfulCachedContent
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
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
        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        if(currentLocale !in Common.allLanguages) {
            currentLocale = if(sharedPref!!.getString(getString(R.string.locale), Common.defaultLanguage) !in Common.allLanguages) {
                Common.defaultLanguage
            } else {
                sharedPref!!.getString(getString(R.string.locale), Common.defaultLanguage)!!
            }
        }
        with (sharedPref!!.edit()) {
            putString(getString(R.string.locale), currentLocale)
            apply()
        }

        init()
    }

    private fun init() {
        val loadHelper = LoadHelper(amountNeeded = 3)

        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
            modelGroups = it
            loadedContent(loadHelper)
        }
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            loadedContent(loadHelper)
        }
        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
            exercises = it
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

        // LOADING STATES
        var isInitialLoaded by mutableStateOf(false)
            private set

        // LOCALISATION
        var currentLocale by mutableStateOf("")
        var isLanguageModalOpen by mutableStateOf(false)

        fun refresh(contentfulContentModels: List<ContentfulContentModel>, callback: (result: Boolean) -> Unit) {
            val loadHelper = LoadHelper(amountNeeded = contentfulContentModels.size)

            contentfulContentModels.forEach { entryType ->
                when (entryType) {
                    ContentfulContentModel.MODEL -> {
                        contentfulCachedContent!!.models = emptyList()
                        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                            models = it
                            loadedContent(loadHelper, callback)
                        }
                    }
                    ContentfulContentModel.MODELGROUP -> {
                        contentfulCachedContent!!.modelGroups = emptyList()
                        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                            modelGroups = it
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
                }
            }
            contentfulCachedContent!!.locale = currentLocale
        }

        fun refreshAll() {
            Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                models = it
            }
            Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                modelGroups = it
            }
            Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
                exercises = it
            }
            contentfulCachedContent!!.locale = currentLocale
        }

        private fun loadedContent(loadHelper: LoadHelper, callback: ((result: Boolean) -> Unit)? = null) {
            loadHelper.whenLoaded {
                isInitialLoaded = true
                callback?.invoke(true)
            }
        }
    }
}
