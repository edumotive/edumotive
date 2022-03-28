package com.djinc.edumotive.utils.contentful

interface ContentfulInfrastructure {
    val parameter: ContentfulParams

    fun fetchAll(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (String) -> Unit
    )
}
