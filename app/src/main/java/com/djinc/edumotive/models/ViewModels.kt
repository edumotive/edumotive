package com.djinc.edumotive.models

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.R
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import java.util.*

enum class EntryType { Models, ModelGroups, Exercises }

class ViewModels : ViewModel() {
    var modelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
        private set
    var models by mutableStateOf(listOf<ContentfulModel>())
        private set
    var exercises by mutableStateOf(listOf<ContentfulExercise>())
        private set
    var linkedModelGroup by mutableStateOf(listOf<ContentfulModelGroup>())
    var activeModel by mutableStateOf(
        ContentfulModel(
            id = "",
            type = "",
            title = "",
            image = "",
            description = "",
            modelUrl = ""
        )
    )
    var activeModelGroup by mutableStateOf(
        ContentfulModelGroup(
            id = "",
            type = "",
            title = "",
            image = "",
            description = "",
            models = mutableListOf(),
            modelUrl = ""
        )
    )

    // LOADING STATES
    var isInitialLoaded by mutableStateOf(false)
    var isModelsLoaded by mutableStateOf(false)
    var isModelGroupsLoaded by mutableStateOf(false)
    var isExercisesLoaded by mutableStateOf(false)
    var isLinkedModelGroupLoaded by mutableStateOf(false)
    var isActiveModelLoaded by mutableStateOf(false)
    var isActiveModelGroupLoaded by mutableStateOf(false)

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
                    isModelsLoaded = true
                    Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
                        models = it
                        isModelsLoaded = false
                        callback.invoke(entriesLoaded())
                    }
                }
                EntryType.ModelGroups -> {
                    isModelGroupsLoaded = true
                    Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                        modelGroups = it
                        isModelGroupsLoaded = false
                        callback.invoke(entriesLoaded())
                    }
                }
                EntryType.Exercises -> {
                    Contentful().fetchAllExercises(errorCallBack = ::errorCatch) {
                        exercises = it
                        isExercisesLoaded = false
                        callback.invoke(entriesLoaded())
                    }
                    callback.invoke(entriesLoaded())
                }
            }
        }
    }

    fun isActiveModelAndLinkedModelGroupLoaded(): Boolean {
        return isActiveModelLoaded && isLinkedModelGroupLoaded
    }

    private fun entriesLoaded(): Boolean {
        return isModelGroupsLoaded && isModelsLoaded && isExercisesLoaded
    }
}
