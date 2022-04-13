package com.djinc.edumotive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.djinc.edumotive.components.menus.BottomBar
import com.djinc.edumotive.components.menus.SideBar
import com.djinc.edumotive.components.modals.LanguageModal
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.navigation.NavGraph
import com.djinc.edumotive.utils.WindowSize

@Composable
fun App(windowSize: WindowSize, viewModels: ViewModels) {
    val navController = rememberNavController()
    if (windowSize == WindowSize.Compact) {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            },
        ) {
            NavGraph(
                navController = navController,
                windowSize = windowSize,
                viewModels = viewModels
            )
        }
        if (viewModels.isLanguageModalOpen) {
            LanguageModal(windowSize = windowSize, viewModels = viewModels)
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
                    windowSize = windowSize,
                    viewModels = viewModels
                )
            }
        }
        if (viewModels.isLanguageModalOpen) {
            LanguageModal(windowSize = windowSize, viewModels = viewModels)
        }
    }
}
