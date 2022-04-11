package com.djinc.edumotive

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MainEdumotive : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        val context = applicationContext
        val currentLocale = context.resources.configuration.locales[0].toLanguageTag()
        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        with (sharedPref!!.edit()) {
            putString(getString(R.string.locale), currentLocale)
            apply()
        }
    }

    companion object {
        var appContext: Context? = null
            private set
        var sharedPref: SharedPreferences? = null
            private set
    }
}
