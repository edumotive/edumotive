package com.djinc.edumotive.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class LoadHelper(
    private var currentAmount: MutableState<Int> = mutableStateOf(0),
    amountNeeded: Int = 0,
    private var maxAmount: MutableState<Int> = mutableStateOf(amountNeeded)
) {
    fun whenLoaded(updateLoading: (Int) -> Unit = {}, doneLoading: () -> Unit) {
        currentAmount.value = currentAmount.value + 1
        updateLoading(currentAmount.value)
        if (currentAmount.value == maxAmount.value) doneLoading()
    }

    fun resetCurrentAmount() {
        currentAmount.value = 0
    }

    fun setCurrentAmount(amount: Int, errorCallback: (errorMessage: String) -> Unit = {}) {
        if (amount > maxAmount.value)
            errorCallback("amount is larger than ${maxAmount.value}")
        else currentAmount.value = amount
    }

    fun setMaxAmount(amount: Int, errorCallback: (errorMessage: String) -> Unit = {}) {
        if (amount < currentAmount.value)
            errorCallback("amount is smaller than ${currentAmount.value}")
        else currentAmount.value = amount
    }
}
