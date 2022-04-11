package com.djinc.edumotive.utils

import android.content.Context
import com.djinc.edumotive.MainEdumotive
import java.util.*

@SuppressWarnings("deprecation")
fun changeLocale(locale: Locale) {
    val resources = MainEdumotive.appContext!!.resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}
