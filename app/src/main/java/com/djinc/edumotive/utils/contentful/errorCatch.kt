package com.djinc.edumotive.utils.contentful

import android.util.Log

fun errorCatch(throwable: Throwable) {
    Log.e("ContentfulAPI", "Error: ", throwable)
}
