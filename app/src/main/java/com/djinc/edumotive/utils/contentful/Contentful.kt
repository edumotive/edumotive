package com.djinc.edumotive.utils.contentful

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.R
import com.djinc.edumotive.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class Contentful(
    private var context: Context = MainEdumotive.appContext!!,
    private var sharedPrefs: SharedPreferences = MainEdumotive.sharedPref!!,
    private var locale: String = sharedPrefs.getString(context.getString(R.string.locale), "en-US")!!,
    private var clientDelivery: CDAClient = CDAClient.builder()
        .setToken(parameterFromBuildConfig().deliveryToken)
        .setSpace(parameterFromBuildConfig().spaceId)
        .build(),
    private var client: CDAClient = clientDelivery,
    override var parameter: ContentfulParams = parameterFromBuildConfig()
) : ContentfulInfrastructure {
    override fun fetchAllModels(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModel>) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.models.isEmpty()
            || MainEdumotive.contentfulCachedContent!!.locale != locale
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val models = client
                        .fetch(CDAEntry::class.java)
                        .withContentType("model")
                        .where("locale", locale)
                        .where("metadata.tags.sys.id" + "[in]", parameterFromBuildConfig().studyTag)
                        .all()
                        .items()
                        .map {
                            ContentfulModel.fromRestEntry(
                                it as CDAEntry
                            )
                        }
                    MainEdumotive.contentfulCachedContent!!.models = models

                    successCallBack(models)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            successCallBack(MainEdumotive.contentfulCachedContent!!.models)
        }
    }

    override fun fetchAllModelGroups(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModelGroup>) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.modelGroups.isEmpty()
            || MainEdumotive.contentfulCachedContent!!.locale != locale
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val modelGroups = client
                        .fetch(CDAEntry::class.java)
                        .withContentType("modelGroup")
                        .where("locale", locale)
                        .where("metadata.tags.sys.id" + "[in]", parameterFromBuildConfig().studyTag)
                        .all()
                        .items()
                        .map {
                            ContentfulModelGroup.fromRestEntry(
                                it as CDAEntry
                            )
                        }

                    MainEdumotive.contentfulCachedContent!!.modelGroups = modelGroups

                    successCallBack(modelGroups)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            successCallBack(MainEdumotive.contentfulCachedContent!!.modelGroups)
        }
    }

    override fun fetchAllExercises(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExercise>) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.exercises.isEmpty()
            || MainEdumotive.contentfulCachedContent!!.locale != locale
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exercises = client
                        .fetch(CDAEntry::class.java)
                        .withContentType("exercise")
                        .where("locale", locale)
                        .where("metadata.tags.sys.id" + "[in]", parameterFromBuildConfig().studyTag)
                        .all()
                        .items()
                        .map {
                            ContentfulExercise.fromRestEntry(
                                it as CDAEntry
                            )
                        }

                    MainEdumotive.contentfulCachedContent!!.exercises = exercises

                    successCallBack(exercises)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            successCallBack(MainEdumotive.contentfulCachedContent!!.exercises)
        }
    }

    override fun fetchAllExercisesManual(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExerciseManual>) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.exercisesManual.isEmpty()
            || MainEdumotive.contentfulCachedContent!!.locale != locale
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exercisesManual = client
                        .fetch(CDAEntry::class.java)
                        .withContentType("exerciseManual")
                        .where("locale", locale)
                        .where("metadata.tags.sys.id" + "[in]", parameterFromBuildConfig().studyTag)
                        .include(4)
                        .all()
                        .items()
                        .map {
                            ContentfulExerciseManual.fromRestEntry(
                                it as CDAEntry
                            )
                        }

                    MainEdumotive.contentfulCachedContent!!.exercisesManual = exercisesManual

                    successCallBack(exercisesManual)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            successCallBack(MainEdumotive.contentfulCachedContent!!.exercisesManual)
        }
    }

    override fun fetchAllExercisesAssemble(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExerciseAssemble>) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.exerciseAssemble.isEmpty()
            || MainEdumotive.contentfulCachedContent!!.locale != locale
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exerciseAssemble = client
                        .fetch(CDAEntry::class.java)
                        .withContentType("exerciseAssemble")
                        .where("locale", locale)
                        .where("metadata.tags.sys.id" + "[in]", parameterFromBuildConfig().studyTag)
                        .include(4)
                        .all()
                        .items()
                        .map {
                            ContentfulExerciseAssemble.fromRestEntry(
                                it as CDAEntry
                            )
                        }

                    MainEdumotive.contentfulCachedContent!!.exerciseAssemble = exerciseAssemble

                    successCallBack(exerciseAssemble)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            successCallBack(MainEdumotive.contentfulCachedContent!!.exerciseAssemble)
        }
    }

    override fun fetchAllExercisesRecognition(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulExerciseRecognition>) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.exerciseRecognition.isEmpty()
            || MainEdumotive.contentfulCachedContent!!.locale != locale
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exerciseRecognition = client
                        .fetch(CDAEntry::class.java)
                        .withContentType("exerciseRecognition")
                        .where("locale", locale)
                        .where("metadata.tags.sys.id" + "[in]", parameterFromBuildConfig().studyTag)
                        .include(4)
                        .all()
                        .items()
                        .map {
                            ContentfulExerciseRecognition.fromRestEntry(
                                it as CDAEntry
                            )
                        }

                    MainEdumotive.contentfulCachedContent!!.exerciseRecognition = exerciseRecognition

                    successCallBack(exerciseRecognition)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            successCallBack(MainEdumotive.contentfulCachedContent!!.exerciseRecognition)
        }
    }

    override fun fetchModelByID(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulModel) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.models.isEmpty()
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val model = ContentfulModel.fromRestEntry(
                        client
                            .fetch(CDAEntry::class.java)
                            .withContentType("model")
                            .where("locale", locale)
                            .one(id)

                    )

                    successCallBack(model)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            val res = MainEdumotive.contentfulCachedContent!!.models.find { model -> model.id == id }
            try {
                successCallBack(res!!)
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
        if(
            MainEdumotive.contentfulCachedContent!!.modelGroups.isEmpty()
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val modelGroup = ContentfulModelGroup.fromRestEntry(
                        client
                            .fetch(CDAEntry::class.java)
                            .withContentType("modelGroup")
                            .where("locale", locale)
                            .one(id)

                    )

                    successCallBack(modelGroup)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            val res = MainEdumotive.contentfulCachedContent!!.modelGroups.find { modelGroup -> modelGroup.id == id }
            try {
                successCallBack(res!!)
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
        if(
            MainEdumotive.contentfulCachedContent!!.exercises.isEmpty()
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exercise = ContentfulExercise.fromRestEntry(
                        client
                            .fetch(CDAEntry::class.java)
                            .withContentType("exercise")
                            .where("locale", locale)
                            .one(id)
                    )

                    successCallBack(exercise)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            val res = MainEdumotive.contentfulCachedContent!!.exercises.find { exercises -> exercises.id == id }
            try {
                successCallBack(res!!)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchLinkedModelGroupById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (List<ContentfulModelGroup>) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.modelGroups.isEmpty()
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val modelGroups = client
                        .fetch(CDAEntry::class.java)
                        .withContentType("modelGroup")
                        .where("locale", locale)
                        .linksToEntryId(id)
                        .include(1)
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
        } else {
            val list = MainEdumotive.contentfulCachedContent!!.modelGroups
            val res = list.filter {
                modelGroup -> modelGroup.models.any { model -> model.id == id }
            }
            successCallBack(res)
        }
    }

    override fun fetchExercisesManualById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExerciseManual) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.exercisesManual.isEmpty()
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exercisesManual = ContentfulExerciseManual.fromRestEntry(
                        client
                            .fetch(CDAEntry::class.java)
                            .withContentType("exerciseManual")
                            .where("locale", locale)
                            .include(2)
                            .one(id)

                    )

                    successCallBack(exercisesManual)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            val res = MainEdumotive.contentfulCachedContent!!.exercisesManual.find { exercisesManual -> exercisesManual.id == id }
            try {
                successCallBack(res!!)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchExercisesAssembleById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExerciseAssemble) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.exerciseAssemble.isEmpty()
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exerciseAssemble = ContentfulExerciseAssemble.fromRestEntry(
                        client
                            .fetch(CDAEntry::class.java)
                            .withContentType("exerciseAssemble")
                            .where("locale", locale)
                            .include(2)
                            .one(id)

                    )

                    successCallBack(exerciseAssemble)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            val res = MainEdumotive.contentfulCachedContent!!.exerciseAssemble.find { exerciseAssemble -> exerciseAssemble.id == id }
            try {
                successCallBack(res!!)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }

    override fun fetchExercisesRecognitionById(
        id: String,
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (ContentfulExerciseRecognition) -> Unit
    ) {
        if(
            MainEdumotive.contentfulCachedContent!!.exerciseRecognition.isEmpty()
        ) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val exerciseRecognition = ContentfulExerciseRecognition.fromRestEntry(
                        client
                            .fetch(CDAEntry::class.java)
                            .withContentType("exerciseRecognition")
                            .where("locale", locale)
                            .include(2)
                            .one(id)

                    )

                    successCallBack(exerciseRecognition)
                } catch (throwable: Throwable) {
                    errorCallBack(throwable)
                }
            }
        } else {
            val res = MainEdumotive.contentfulCachedContent!!.exerciseRecognition.find { exerciseRecognition -> exerciseRecognition.id == id }
            try {
                successCallBack(res!!)
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }
}
