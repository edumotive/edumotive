package com.djinc.edumotive.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ViewModels : ViewModel() {
    var modelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
        private set
    var models by mutableStateOf(listOf<ContentfulModel>())
        private set
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
            models = emptyList(),
            modelUrl = ""
        )
    )
    var isLoading by mutableStateOf(true)

    init {
        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
            modelGroups = it
        }
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            isLoading = false
        }
    }

    fun refreshModelGroups() {
        Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
            modelGroups = it
        }
    }

    fun refreshModels(callback: (result: Boolean) -> Unit) {
        Contentful().fetchAllModels(errorCallBack = ::errorCatch) {
            models = it
            callback.invoke(false)
        }
    }
}
