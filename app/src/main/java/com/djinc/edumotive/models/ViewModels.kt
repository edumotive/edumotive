package com.djinc.edumotive.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import kotlinx.coroutines.launch

class ViewModels : ViewModel() {
    var modelGroups by mutableStateOf(listOf<ContentfulModelGroup>())
        private set

    init {
        viewModelScope.launch {
            Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                modelGroups = it
            }
        }
    }

    fun refreshModelGroups() {
        viewModelScope.launch {
            Contentful().fetchAllModelGroups(errorCallBack = ::errorCatch) {
                modelGroups = it
            }
        }
    }
}
