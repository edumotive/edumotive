package com.djinc.edumotive.models

data class ContentfulModel(
    val id: String = "",
    val type: String = "",
    val title: String = "",
    val image: String = "",
    val description: String = "",
    val modelUrl: String = "",
) {
    companion object
}

data class ContentfulModelGroup(
    val id: String = "",
    val type: String = "",
    val title: String = "",
    val image: String = "",
    val description: String = "",
    val models: MutableList<ContentfulModel> = mutableListOf(),
    val modelUrl: String = "",
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
