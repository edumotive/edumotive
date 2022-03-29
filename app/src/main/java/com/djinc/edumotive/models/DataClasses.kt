package com.djinc.edumotive.models

import io.github.sceneview.ar.node.ArModelNode

data class Part(val id: String, val name: String)
data class Exercise(val id: String, val name: String, val description: String)
data class ExerciseStep(val name: String)

data class ContentfulModel (
    val modelURL : String,
    val modelName : String
)

data class ARModel (
    val modelNode : ArModelNode
)
