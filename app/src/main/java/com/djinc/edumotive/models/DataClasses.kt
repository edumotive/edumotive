package com.djinc.edumotive.models

import io.github.sceneview.ar.node.ArModelNode

data class Part(val id: String, val name: String, val imageUrl: String)
data class Exercise(val id: String, val name: String, val imageUrl: String, val description: String)
data class ExerciseStep(val name: String)

data class ContentfulModel (
    val id : String,
    val title : String,
    val image : String,
    val description : String,
    val modelURL : String,
) {
    companion object
}

data class ContentfulModelGroup (
    val id : String,
    val title : String,
    val image : String,
    val description : String,
    val models : List<ContentfulModel>
) {
    companion object
}

data class ContentfulExercise (
    val id : String,
    val title : String,
    val image : String,
    val description : String,
    val chapter : String,
    val minTime : Int,
    val maxTime : Int,
    val steps: List<String>,
    val models : ContentfulModelGroup
) {
    companion object
}

data class ARModel (
    val modelNode : ArModelNode
)
