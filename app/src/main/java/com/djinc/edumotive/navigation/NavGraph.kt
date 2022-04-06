package com.djinc.edumotive.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.djinc.edumotive.models.EntryType
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.screens.*
import com.djinc.edumotive.utils.WindowSize
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph(navController: NavHostController, windowSize: WindowSize, viewModels: ViewModels) {
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { isRefreshing = true },
            ) {
                Dashboard(nav = navController, windowSize = windowSize, viewModels = viewModels)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    viewModels.refresh(
                        listOf(
                            EntryType.ModelGroups,
                            EntryType.Models,
                            EntryType.Exercises
                        )
                    ) {
                        isRefreshing = it
                    }
                }
            }
        }
        composable(route = Screen.Parts.route) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { isRefreshing = true },
            ) {
                Parts(nav = navController, windowSize = windowSize, viewModels = viewModels)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    viewModels.refresh(listOf(EntryType.Models, EntryType.ModelGroups)) {
                        isRefreshing = it
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
                modelType = modelType!!,
                nav = navController,
                windowSize = windowSize,
                viewModels = viewModels
            )
        }
        composable(route = Screen.Exercises.route) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { isRefreshing = true },
            ) {
                Exercises(nav = navController, windowSize = windowSize, viewModels = viewModels)
            }
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    viewModels.refresh(listOf(EntryType.Exercises)) {
                        isRefreshing = it
                    }
                }
            }
        }
        composable(
            route = Screen.Exercise.route,
            arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("exerciseId")?.let {
                ExerciseDetails(
                    exerciseId = it,
                    nav = navController,
                    windowSize = windowSize,
                    viewModels = viewModels
                )
            }
        }
    }
}
