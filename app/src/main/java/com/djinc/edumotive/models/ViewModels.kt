package com.djinc.edumotive.models

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch

enum class EntryType { Models, ModelGroups, Exercises }

class ViewModels : ViewModel() {
    var modelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
        private set
    var models by mutableStateOf(listOf<ContentfulModel>())
        private set
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
            isModelGroupsLoaded = true
            isInitialLoaded = entriesLoaded()
        }
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            isModelsLoaded = true
            isInitialLoaded = entriesLoaded()
        }
        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
            exercises = it
            isExercisesLoaded = true
            isInitialLoaded = entriesLoaded()
        }
    }

    fun refresh(entryTypes: List<EntryType>, callback: (result: Boolean) -> Unit) {
        entryTypes.forEach { entryType ->
            when (entryType) {
                EntryType.Models -> {
                    isModelsLoaded = false
                    Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                        models = it
                        isModelsLoaded = true
                        callback.invoke(entriesLoaded())
                    }
                }
                EntryType.ModelGroups -> {
                    isModelGroupsLoaded = false
                    Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                        models = it
                        isModelsLoaded = true
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
                    callback.invoke(entriesLoaded())
                }
            }
        }
    }

    fun refreshAll() {
        isModelsLoaded = false
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            isModelsLoaded = true
        }
        isModelGroupsLoaded = false
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            isModelsLoaded = true
        }
        isExercisesLoaded = false
        Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
            exercises = it
            isExercisesLoaded = true
        }
    }

    fun isActiveModelAndLinkedModelGroupLoaded(): Boolean {
        return isActiveModelLoaded && isLinkedModelGroupLoaded
    }

    private fun entriesLoaded(): Boolean {
        return isModelGroupsLoaded && isModelsLoaded && isExercisesLoaded
    }
}
