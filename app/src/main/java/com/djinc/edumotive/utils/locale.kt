package com.djinc.edumotive.utils

import android.content.Context
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.R
import java.util.*

@SuppressWarnings("deprecation")
fun changeLocale(context: Context, locale: Locale) {
    with (MainEdumotive.sharedPref!!.edit()) {
        putString(context.getString(R.string.locale), locale.toLanguageTag())
        apply()
    }

    val resources = context.resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}
