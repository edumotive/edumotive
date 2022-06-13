package com.djinc.edumotive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.components.menus.BottomBar
import com.djinc.edumotive.components.menus.SideBar
import com.djinc.edumotive.components.modals.LanguageModal
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.navigation.NavGraph

@Composable
fun App(windowSize: WindowSize) {
    val navController = rememberNavController()
    if (windowSize == WindowSize.Compact) {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            },
        ) {
            NavGraph(
                navController = navController,
                windowSize = windowSize
            )
        }
        if (MainEdumotive.isLanguageModalOpen) {
            LanguageModal(windowSize = windowSize)
        }
    } else {
        Row {
            SideBar(navController = navController)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                NavGraph(
                    navController = navController,
                    windowSize = windowSize
                )
            }
        }
        if (MainEdumotive.isLanguageModalOpen) {
            LanguageModal(windowSize = windowSize)
        }
    }
}
