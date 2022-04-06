package com.djinc.edumotive.utils

import android.os.Parcelable
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class modelProps(val cModel: @RawValue ContentfulModel? = null, val cModelGroup: @RawValue ContentfulModelGroup? = null) : Parcelable {
    val id get() = cModel?.id ?: cModelGroup?.id
    val title get() = cModel?.title ?: cModelGroup?.title
    val image get() = cModel?.image ?: cModelGroup?.image
    val description get() = cModel?.description ?: cModelGroup?.description
    val modelUrl get() = cModel?.modelUrl ?: cModelGroup?.modelUrl
}
