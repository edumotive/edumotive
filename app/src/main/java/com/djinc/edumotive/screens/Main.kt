package com.djinc.edumotive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.djinc.edumotive.components.BottomBar
import com.djinc.edumotive.components.SideBar
import com.djinc.edumotive.navigation.NavGraph
import com.djinc.edumotive.utils.WindowSize

@Composable
fun Main(windowSize: WindowSize) {
    val navController = rememberNavController()
    if (windowSize == WindowSize.Compact) {
        Scaffold(bottomBar = { BottomBar(navController = navController)}) {
            NavGraph(navController = navController)
        }
    } else {
        Row() {
            SideBar(navController = navController)
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)) {
                NavGraph(navController = navController)
            }
        }
    }
}