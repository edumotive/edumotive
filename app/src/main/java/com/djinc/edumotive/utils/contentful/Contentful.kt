package com.djinc.edumotive.utils.contentful

import android.content.Context
import android.content.SharedPreferences
import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.R
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
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
            MainEdumotive.contentfulCachedContent!!.modelGroups.isEmpty()
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
                modelGroup -> modelGroup.models.contains(
                    MainEdumotive.contentfulCachedContent!!.models.find { model -> model.id == id }
                )
            }
            successCallBack(res)
        }
    }
}
