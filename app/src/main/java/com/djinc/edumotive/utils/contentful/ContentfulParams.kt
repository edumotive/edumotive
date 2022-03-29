package com.djinc.edumotive.utils.contentful

import com.djinc.edumotive.BuildConfig

data class ContentfulParams(
    var spaceId: String = "",
    var previewToken: String = "",
    var deliveryToken: String = "",
    var host: String = ""
)

fun parameterFromBuildConfig(): ContentfulParams =
    ContentfulParams(
        spaceId = BuildConfig.CONTENTFUL_SPACE_ID,
        deliveryToken = BuildConfig.CONTENTFUL_DELIVERY_TOKEN,
        previewToken = BuildConfig.CONTENTFUL_PREVIEW_TOKEN,
        host = BuildConfig.CONTENTFUL_HOST,
    )
