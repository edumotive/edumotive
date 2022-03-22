package com.djinc.edumotive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.djinc.edumotive.navigation.Screen

@Composable
fun SideBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Dashboard,
        Screen.Parts,
        Screen.Exercises
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            // LOGO
            Row() {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .background(Color.Black)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("EduMotive")
            }
            Spacer(modifier = Modifier.height(24.dp))
            // LINKS TO DIFFERENT SCREENS
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ColumnScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    Row(modifier = Modifier.clickable {
        val active =
            currentDestination?.hierarchy?.any { it.route == screen.route } == true

        if (!active) navController.navigate(screen.route)
    }) {
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(screen.title)
    }
}
