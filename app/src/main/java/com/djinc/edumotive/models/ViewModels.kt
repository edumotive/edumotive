package com.djinc.edumotive.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.constants.EntryType
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import java.time.LocalDate

class ViewModels : ViewModel() {
    var modelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
        private set
    var filteredModelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
    var models by mutableStateOf(listOf<ContentfulModel>())
        private set
    var filteredModels by mutableStateOf(listOf<ContentfulModel>())
    var exercises by mutableStateOf(listOf<ContentfulExercise>())
        private set
    var linkedModelGroup by mutableStateOf(listOf<ContentfulModelGroup>())
    var activeModel by mutableStateOf(ContentfulModel())
    var activeModelGroup by mutableStateOf(ContentfulModelGroup())
    var activeExercise by mutableStateOf(ContentfulExercise())

    // LOADING STATES
    var isInitialLoaded by mutableStateOf(false)
    var isModelsLoaded by mutableStateOf(false)
    var isModelGroupsLoaded by mutableStateOf(false)
    var isExercisesLoaded by mutableStateOf(false)
    var isLinkedModelGroupLoaded by mutableStateOf(false)
    var isActiveModelLoaded by mutableStateOf(false)
    var isActiveModelGroupLoaded by mutableStateOf(false)
    var isActiveExerciseLoaded by mutableStateOf(false)

    // LOCALISATION
    var isLanguageModalOpen by mutableStateOf(false)
    var currentLocale by mutableStateOf("")

    init {
        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
            modelGroups = it
            filteredModelGroups = it
            isModelGroupsLoaded = true
            isInitialLoaded = entriesLoaded()
        }
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            filteredModels = it
            isModelsLoaded = true
            isInitialLoaded = entriesLoaded()
        }
        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
            exercises = it
            isExercisesLoaded = true
            isInitialLoaded = entriesLoaded()
        }

        MainEdumotive.contentfulCachedContent!!.date = LocalDate.now().toString()
    }

    fun refresh(entryTypes: List<EntryType>, callback: (result: Boolean) -> Unit) {
        entryTypes.forEach { entryType ->
            when (entryType) {
                EntryType.Models -> {
                    isModelsLoaded = false
                    Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                        models = it
                        filteredModels = it
                        isModelsLoaded = true
                        callback.invoke(entriesLoaded())
                    }
                }
                EntryType.ModelGroups -> {
                    isModelGroupsLoaded = false
                    Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                        modelGroups = it
                        filteredModelGroups = it
                        isModelGroupsLoaded = true
                        callback.invoke(entriesLoaded())
                    }
                }
                EntryType.Exercises -> {
                    isExercisesLoaded = false
                    Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
                        exercises = it
                        isExercisesLoaded = true
                        callback.invoke(entriesLoaded())
                    }
                }
            }
        }
        MainEdumotive.contentfulCachedContent!!.locale = currentLocale
    }

    fun refreshAll() {
        isModelsLoaded = false
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            filteredModels = it
            isModelsLoaded = true
        }
        isModelGroupsLoaded = false
        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
            modelGroups = it
            filteredModelGroups = it
            isModelGroupsLoaded = true
        }
        isExercisesLoaded = false
        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
            exercises = it
            isExercisesLoaded = true
        }
        MainEdumotive.contentfulCachedContent!!.locale = currentLocale
    }

    fun isActiveModelAndLinkedModelGroupLoaded(): Boolean {
        return isActiveModelLoaded && isLinkedModelGroupLoaded
    }

    private fun entriesLoaded(): Boolean {
        return isModelGroupsLoaded && isModelsLoaded && isExercisesLoaded
    }
}
