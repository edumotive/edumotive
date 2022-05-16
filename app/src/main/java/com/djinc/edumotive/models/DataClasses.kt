package com.djinc.edumotive.models

import com.djinc.edumotive.constants.ContentfulContentModel
import io.github.sceneview.ar.node.ArModelNode

data class ContentfulModel(
    val id: String = "",
    val type: ContentfulContentModel = ContentfulContentModel.MODEL,
    val title: String = "",
    val image: String = "",
    val description: String = "",
    val modelUrl: String = "",
    var arModel: ArModelNode? = null
) {
    companion object
}

data class ContentfulModelGroup(
    val id: String = "",
    val type: ContentfulContentModel = ContentfulContentModel.MODELGROUP,
    val title: String = "",
    val image: String = "",
    val description: String = "",
    val models: MutableList<ContentfulModel> = mutableListOf()
) {
    companion object
}

data class ContentfulExercise(
    val id: String = "",
    val title: String = "",
    val image: String = "",
    val description: String = "",
    val chapter: String = "",
    val minTime: Int = 0,
    val maxTime: Int = 0,
    val steps: List<String> = emptyList(),
    val models: List<ContentfulModel> = emptyList()
) {
    companion object
}

data class ContentfulCachedContent(
    var date: String = "",
    var locale: String = "",
    var models: List<ContentfulModel> = emptyList(),
    var modelGroups: List<ContentfulModelGroup> = emptyList(),
    var exercises: List<ContentfulExercise> = emptyList()
)
