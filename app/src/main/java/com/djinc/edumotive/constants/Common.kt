package com.djinc.edumotive.constants

class Common {
    companion object {
        const val netherlandsLanguage: String = "nl"
        const val netherlandsCountry: String = "NL"
        const val netherlandsTag: String = "$netherlandsLanguage-$netherlandsCountry"
        const val englishLanguage: String = "en"
        const val usCountry: String = "US"
        const val usTag: String = "$englishLanguage-$usCountry"
        const val defaultLanguage: String = usTag
        val allLanguages: Array<String> = arrayOf(netherlandsTag, usTag)
    }
}
