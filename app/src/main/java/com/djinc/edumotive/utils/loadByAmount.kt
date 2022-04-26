package com.djinc.edumotive.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

 open class loadHelper (
     private var amountLoaded: MutableState<Int> = mutableStateOf(0)
 ) {
     fun whenLoaded(amountNeeded: Int, updateLoading: (Int) -> Unit, doneLoading: () -> Unit) {
        amountLoaded.value = amountLoaded.value + 1

        updateLoading(amountLoaded.value)

        if (amountLoaded.value == amountNeeded) doneLoading()
    }
}

