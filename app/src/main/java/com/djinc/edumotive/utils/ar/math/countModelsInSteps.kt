package com.djinc.edumotive.utils.ar.math

import androidx.compose.runtime.mutableStateOf
import com.djinc.edumotive.models.ContentfulModelStep

fun countModelsInSteps(steps: MutableList<ContentfulModelStep>): Int {
    val amount = mutableStateOf(0)
    steps.forEach { step ->
        amount.value = amount.value + step.getModelCount()
    }
    return amount.value
}
