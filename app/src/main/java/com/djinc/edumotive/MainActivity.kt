package com.djinc.edumotive

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.djinc.edumotive.constants.Common
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.screens.App
import com.djinc.edumotive.ui.theme.EdumotiveTheme
import com.djinc.edumotive.utils.SplitTag
import com.djinc.edumotive.utils.WindowSize
import com.djinc.edumotive.utils.changeLocale
import com.djinc.edumotive.utils.rememberWindowSizeClass
import java.util.*

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ViewModels>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSize = rememberWindowSizeClass()
            if (windowSize != WindowSize.Compact) requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            EdumotiveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val currentLocale = MainEdumotive.sharedPref!!.getString(
                        getString(R.string.locale),
                        Common.defaultLanguage
                    )

                    if (currentLocale != null) {
                        changeLocale(
                            LocalContext.current,
                            Locale(SplitTag(currentLocale).language, SplitTag(currentLocale).country)
                        )
                        viewModel.currentLocale = currentLocale
                    }
                    App(windowSize, viewModel)
                }
            }
        }
    }
}
