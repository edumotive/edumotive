package com.djinc.edumotive.navigation

import com.djinc.edumotive.R

sealed class Screen(
    val route: String,
    val title: String,
    val icon: Int?
) {
    object Dashboard : Screen(
        route = "dashboard",
        title = "dashboard",
        icon = R.drawable.ic_dashboard
    )

    object Parts : Screen(
        route = "parts",
        title = "parts",
        icon = R.drawable.ic_parts
    )

    object Part : Screen(
        route = "part/{partId}/{modelType}",
        title = "part",
        icon = null
    )

    object Exercises : Screen(
        route = "exercises",
        title = "exercises",
        icon = R.drawable.ic_exercises
    )

    object Exercise : Screen(
        route = "exercise/{exerciseId}/{exerciseType}",
        title = "exercise",
        icon = null
    )
}
