package com.djinc.edumotive.utils.contentful

import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class Contentful (
    private var clientDelivery: CDAClient = CDAClient.builder()
        .setToken(parameterFromBuildConfig().deliveryToken)
        .setSpace(parameterFromBuildConfig().spaceId)
        .build(),
    var client: CDAClient = clientDelivery,
    override var parameter: ContentfulParams = parameterFromBuildConfig()
) : ContentfulInfrastructure {
    override fun fetchAllModels(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModel>) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val models = client
                    .fetch(CDAEntry::class.java)
                    .withContentType("model")
                    .all()
                    .items()
                    .map {
                        ContentfulModel.fromRestEntry(
                            it as CDAEntry
                        )
                    }

                successCallBack(models)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchAllModelGroups(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModelGroup>) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val modelGroups = client
                    .fetch(CDAEntry::class.java)
                    .withContentType("modelGroup")
                    .all()
                    .items()
                    .map {
                        ContentfulModelGroup.fromRestEntry(
                            it as CDAEntry
                        )
                    }

                successCallBack(modelGroups)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchAllExercises(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExercise>) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val exercises = client
                    .fetch(CDAEntry::class.java)
                    .withContentType("exercise")
                    .all()
                    .items()
                    .map {
                        ContentfulExercise.fromRestEntry(
                            it as CDAEntry
                        )
                    }

                successCallBack(exercises)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchModelByID(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulModel) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val model = ContentfulModel.fromRestEntry(
                    client
                        .fetch(CDAEntry::class.java)
                        .withContentType("model")
                        .one(id)

                )

                successCallBack(model)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchModelGroupById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulModelGroup) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val modelGroup = ContentfulModelGroup.fromRestEntry(
                    client
                        .fetch(CDAEntry::class.java)
                        .withContentType("modelGroup")
                        .one(id)

                )

                successCallBack(modelGroup)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchExercisesById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExercise) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val exercise = ContentfulExercise.fromRestEntry(
                    client
                        .fetch(CDAEntry::class.java)
                        .withContentType("exercise")
                        .one(id)

                )

                successCallBack(exercise)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }
}
