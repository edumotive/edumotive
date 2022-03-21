package com.djinc.edumotive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.Sidebar
import com.djinc.edumotive.screens.Dashboard
import com.djinc.edumotive.screens.Exercises
import com.djinc.edumotive.screens.Main
import com.djinc.edumotive.screens.Parts
import com.djinc.edumotive.ui.theme.EdumotiveTheme

enum class Screens {
    DASHBOARD, PARTS, EXERCISES
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdumotiveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main()
//                    Row {
//                        Sidebar()
//                        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
//                            when (Screens.DASHBOARD) {
//                                Screens.DASHBOARD -> Dashboard()
//                                Screens.PARTS -> Parts()
//                                Screens.EXERCISES -> Exercises()
//                            }
//                        }
//                    }
                }
            }
        }
    }
}