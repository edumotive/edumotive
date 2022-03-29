package com.djinc.edumotive.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import com.djinc.edumotive.R

sealed class Screen(
        val route: String,
        val title: String,
        val icon: Int?
) {
    object Dashboard : Screen(
            route = "dashboard",
            title = "Dashboard",
            icon = R.drawable.ic_dashboard
    )

    object Parts : Screen(
            route = "parts",
            title = "Onderdelen",
            icon = R.drawable.ic_parts
    )

    object Part : Screen(
            route = "part/{partId}",
            title = "Onderdeel",
            icon = null
    )

    object Exercises : Screen(
            route = "exercises",
            title = "Oefeningen",
            icon = R.drawable.ic_exercises
    )

    object Exercise : Screen(
            route = "exercise/{exerciseId}",
            title = "Oefening",
            icon = null
    )
}
