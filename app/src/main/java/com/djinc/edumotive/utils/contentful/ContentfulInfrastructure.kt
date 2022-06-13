package com.djinc.edumotive.utils.contentful

import com.djinc.edumotive.models.*

interface ContentfulInfrastructure {
    val parameter: ContentfulParams

    fun fetchAllModels(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModel>) -> Unit
    )

    fun fetchAllModelGroups(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModelGroup>) -> Unit
    )

    fun fetchAllExercises(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExercise>) -> Unit
    )

    fun fetchAllExercisesManual(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExerciseManual>) -> Unit
    )

    fun fetchAllExercisesAssemble(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExerciseAssemble>) -> Unit
    )

    fun fetchAllExercisesRecognition(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExerciseRecognition>) -> Unit
    )

    fun fetchModelByID(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulModel) -> Unit
    )

    fun fetchModelGroupById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulModelGroup) -> Unit
    )

    fun fetchExercisesById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExercise) -> Unit
    )

    fun fetchLinkedModelGroupById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModelGroup>) -> Unit
    )

    fun fetchExercisesManualById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExerciseManual) -> Unit
    )

    fun fetchExercisesAssembleById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExerciseAssemble) -> Unit
    )

    fun fetchExercisesRecognitionById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExerciseRecognition) -> Unit
    )
}
