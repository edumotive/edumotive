package com.djinc.edumotive.utils.ar.math

import androidx.compose.runtime.mutableStateOf
import com.djinc.edumotive.models.ContentfulModelStep

fun countModelsInSteps(steps: MutableList<ContentfulModelStep>): Int {
    val amount = mutableStateOf(0)
    steps.forEach { step ->
        if(step.models.isNotEmpty()) amount.value = amount.value + step.models.size
        else if (step.modelGroup != null) amount.value = amount.value + step.modelGroup.models.size
    }
    return amount.value
}
