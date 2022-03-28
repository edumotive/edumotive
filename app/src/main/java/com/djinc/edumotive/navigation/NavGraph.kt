package com.djinc.edumotive.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        composable(
                route = Screen.Part.route,
                arguments = listOf(navArgument("partId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("partId")?.let {
                PartDetails(
                        partId = it,
                        nav = navController
                )
            }
        }
        composable(route = Screen.Exercises.route) {
            Exercises(nav = navController)
        }
        composable(
                route = Screen.Exercise.route,
                arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("exerciseId")?.let {
                ExerciseDetails(
                        exerciseId = it,
                        nav = navController
                )
            }
        }
    }
}
