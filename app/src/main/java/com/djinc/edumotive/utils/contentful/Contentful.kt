package com.djinc.edumotive.utils.contentful

import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
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
    override fun fetchAll(
        errorCallBack: (Throwable) -> Unit,
        successCallBack: (String) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val array = client
                    .fetch(CDAEntry::class.java)
                    .all()
                    .items()

                successCallBack(array.toString())
            } catch (throwable: Throwable) {
                errorCallBack(throwable)
            }
        }
    }
}
