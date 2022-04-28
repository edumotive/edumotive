package com.djinc.edumotive.utils.contentful

import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import kotlinx.coroutines.*

private var debounceJobModel: Job? = null
private var debounceJobModelGroup: Job? = null

private const val charMinimum = 2

fun filterModelList(
    listModel: List<ContentfulModel>,
    filter: String,
    debounceDelay: Long = 300L,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    callback: (List<ContentfulModel>) -> Unit
) {
    debounceJobModel?.cancel()
    debounceJobModel = coroutineScope.launch {
        delay(debounceDelay)
        val value = if (filter.chars()
                .count() > charMinimum
        ) listModel.filter { model -> model.title.lowercase().contains(filter.lowercase()) } else listModel
        callback(value)
    }
}

fun filterModelGroupList(
    listModelGroup: List<ContentfulModelGroup>,
    filter: String,
    debounceDelay: Long = 300L,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    callback: (List<ContentfulModelGroup>) -> Unit
) {
    debounceJobModelGroup?.cancel()
    debounceJobModelGroup = coroutineScope.launch {
        delay(debounceDelay)
        val value = if (filter.chars()
                .count() > charMinimum
        ) listModelGroup.filter { model -> model.title.lowercase().contains(filter.lowercase()) } else listModelGroup
        callback(value)
    }
}
