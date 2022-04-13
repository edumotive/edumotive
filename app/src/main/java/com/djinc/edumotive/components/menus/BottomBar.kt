package com.djinc.edumotive.components.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.djinc.edumotive.navigation.Screen
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary
import com.djinc.edumotive.ui.theme.fonts

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Dashboard,
        Screen.Parts,
        Screen.Exercises
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .height(65.dp)
            .background(Background)
    ) {
        Row {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    screenWidth = screenWidth
                )
            }
        }
    }
}

@Composable
fun AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    screenWidth: Dp
) {
    val active = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    val title = stringResource(LocalContext.current.resources
        .getIdentifier(screen.title.lowercase(), "string", LocalContext.current.packageName)
    )
    val interactionSource = remember { MutableInteractionSource() }
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .fillMaxHeight()
        .width(screenWidth / 3)
        .clickable(interactionSource = interactionSource, indication = null) {
            if (!active) navController.navigate(screen.route)
        }
    ) {
        if (!active) {
            screen.icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = "$title navigation icon"
                )
            }
        } else {
            val yPaddingOuter = 20.dp
            val yPaddingInner = 16.dp
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .offset(y = -yPaddingOuter)
                    .requiredHeight(65.dp + yPaddingInner)
            ) {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .clip(CircleShape)
                        .width(56.dp)
                        .height(56.dp)
                        .background(PinkSecondary)
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(CircleShape)
                            .width(50.dp)
                            .height(50.dp)
                            .background(PinkPrimary)
                            .padding(8.dp)
                    ) {
                        screen.icon?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = "$title navigation icon",
                                tint = Background
                            )
                        }
                    }
                }
                Text(
                    text = title,
                    fontFamily = fonts,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = PinkPrimary
                )
            }
        }
    }
}
