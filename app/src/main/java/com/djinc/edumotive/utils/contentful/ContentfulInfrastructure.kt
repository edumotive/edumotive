package com.djinc.edumotive.utils.contentful

import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ContentfulLocale
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup

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

    fun fetchAllLocales(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulLocale>) -> Unit
    )
}
