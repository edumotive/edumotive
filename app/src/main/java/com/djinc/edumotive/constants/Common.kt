package com.djinc.edumotive.constants

class Common {
    companion object {
        private const val netherlandsLanguage: String = "nl"
        private const val netherlandsCountry: String = "NL"
        private const val netherlandsTag: String = "$netherlandsLanguage-$netherlandsCountry"
        private const val englishLanguage: String = "en"
        private const val usCountry: String = "US"
        private const val usTag: String = "$englishLanguage-$usCountry"
        const val defaultLanguage: String = usTag
        val allLanguages: Array<String> = arrayOf(netherlandsTag, usTag)
    }
}
