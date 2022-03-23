package com.djinc.edumotive.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.djinc.edumotive.screens.Dashboard
import com.djinc.edumotive.screens.Exercises
import com.djinc.edumotive.screens.Parts

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            Dashboard()
        }
        composable(route = Screen.Parts.route) {
            Parts()
        }
        composable(route = Screen.Exercises.route) {
            Exercises()
        }
    }
}