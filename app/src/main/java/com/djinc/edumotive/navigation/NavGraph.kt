package com.djinc.edumotive.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.djinc.edumotive.screens.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            Dashboard(nav = navController)
        }
        composable(route = Screen.Parts.route) {
            Parts(nav = navController)
        }
        composable(route = Screen.PartDetails.route) {
            PartDetails()
        }
        composable(route = Screen.Exercises.route) {
            Exercises(nav = navController)
        }
        composable(route = Screen.ExerciseDetails.route) {
            ExerciseDetails()
        }
    }
}