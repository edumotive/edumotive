package com.djinc.edumotive.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector?
) {
    object Dashboard : Screen(
        route = "dashboard",
        title = "Dashboard",
        icon = Icons.Default.Home
    )

    object Parts : Screen(
        route = "parts",
        title = "Onderdelen",
        icon = Icons.Default.Phone
    )

    object Part : Screen(
        route = "part/{partId}",
        title = "Onderdeel",
        icon = null
    )

    object Exercises : Screen(
        route = "exercises",
        title = "Oefeningen",
        icon = Icons.Default.Favorite
    )

    object Exercise : Screen(
        route = "exercise/{exerciseId}",
        title = "Oefening",
        icon = null
    )
}
