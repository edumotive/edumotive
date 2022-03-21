package com.djinc.edumotive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.djinc.edumotive.components.Sidebar
import com.djinc.edumotive.navigation.NavGraph

@Composable
fun Main() {
    val navController = rememberNavController()
    Row() {
        Sidebar(navController)
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)) {
                NavGraph(navController = navController)
        }
    }
}