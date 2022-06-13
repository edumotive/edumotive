package com.djinc.edumotive.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.screens.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph(navController: NavHostController, windowSize: WindowSize) {
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { isRefreshing = true },
            ) {
                Dashboard(nav = navController, windowSize = windowSize)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    MainEdumotive.refresh(
                        listOf(
                            ContentfulContentModel.MODELGROUP,
                            ContentfulContentModel.MODEL,
                            ContentfulContentModel.EXERCISEASSEMBLE,
                            ContentfulContentModel.EXERCISEMANUAL,
                            ContentfulContentModel.EXERCISERECOGNITION
                        )
                    ) {
                        isRefreshing = !it
                    }
                }
            }
        }
        composable(route = Screen.Parts.route) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { isRefreshing = true },
            ) {
                Parts(nav = navController, windowSize = windowSize)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    MainEdumotive.refresh(
                        listOf(
                            ContentfulContentModel.MODELGROUP,
                            ContentfulContentModel.MODEL
                        )
                    ) {
                        isRefreshing = !it
                    }
                }
            }
        }
        composable(
            route = Screen.Part.route,
            arguments = listOf(
                navArgument("partId") { type = NavType.StringType },
                navArgument("modelType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val partId = backStackEntry.arguments?.getString("partId")
            val modelType = backStackEntry.arguments?.getString("modelType")
            PartDetails(
                partId = partId!!,
                modelType = ContentfulContentModel.valueOf(modelType!!),
                nav = navController,
                windowSize = windowSize
            )
        }
        composable(route = Screen.Exercises.route) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { isRefreshing = true },
            ) {
                Exercises(nav = navController, windowSize = windowSize)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    MainEdumotive.refresh(
                        listOf(
                            ContentfulContentModel.EXERCISEASSEMBLE,
                            ContentfulContentModel.EXERCISEMANUAL,
                            ContentfulContentModel.EXERCISERECOGNITION
                        )
                    ) {
                        isRefreshing = !it
                    }
                }
            }
        }
        composable(
            route = Screen.Exercise.route,
            arguments = listOf(
                navArgument("exerciseId") { type = NavType.StringType },
                navArgument("exerciseType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")
            val exerciseType = backStackEntry.arguments?.getString("exerciseType")
            ExerciseDetails(
                exerciseId = exerciseId!!,
                exerciseType = ContentfulContentModel.valueOf(exerciseType!!),
                nav = navController,
                windowSize = windowSize
            )
        }
    }
}
