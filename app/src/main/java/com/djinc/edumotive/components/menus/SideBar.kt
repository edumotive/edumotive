package com.djinc.edumotive.components.menus

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.djinc.edumotive.R
import com.djinc.edumotive.navigation.Screen
import com.djinc.edumotive.ui.theme.*

@Composable
fun SideBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Dashboard,
        Screen.Parts,
        Screen.Exercises
    )
    LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var isMinimized by remember { mutableStateOf(false) }
    val sideBarSize: Dp by animateDpAsState(if (!isMinimized) 220.dp else 60.dp)

    Box {
        Surface(
            shape = RectangleShape,
            color = Background,
            elevation = 12.dp,
            modifier = Modifier
                .fillMaxHeight()
                .width(sideBarSize)
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.padding(top = if (sideBarSize == 220.dp) 11.dp else 20.dp)) {
                    // LOGO
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edumotive),
                            contentDescription = "EduMotive logo"
                        )
                        if (sideBarSize == 220.dp) {
                            Text(
                                text = "EduMotive",
                                fontSize = 26.sp,
                                fontFamily = fonts,
                                style = TextStyle(color = PinkPrimary),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    // LINKS TO DIFFERENT SCREENS
                    screens.forEach { screen ->
                        AddItem(
                            screen = screen,
                            currentDestination = currentDestination,
                            sideBarSize = sideBarSize,
                            navController = navController
                        )
                    }
                }
                // WAVES
                Image(
                    painter = painterResource(id = R.drawable.wave),
                    contentDescription = "Waves",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(sideBarSize)
                )
            }
        }
        // SIDEBAR MINIMIZE BUTTON
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxHeight()
                .width(sideBarSize)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .offset(x = 18.dp)
                    .clip(CircleShape)
                    .clickable {
                        isMinimized = !isMinimized
                    }
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                TextSecondary,
                                Color.Transparent
                            ),
                            radius = 150f
                        )
                    )
                    .background(Background)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrows_right),
                    contentDescription = "Minimize sidebar",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .rotate(if (sideBarSize == 220.dp) 180f else 0f)
                )
            }
        }
    }
}

@Composable
fun AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    sideBarSize: Dp,
    navController: NavHostController
) {
    val active = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val title = stringResource(
        LocalContext.current.resources
            .getIdentifier(screen.title.lowercase(), "string", LocalContext.current.packageName)
    )
    Box(
        contentAlignment = Alignment.CenterStart, modifier = Modifier
            .padding(8.dp)
            .width(sideBarSize)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                if (!active) navController.navigate(screen.route)
            }
            .then(if (active) Modifier.background(PinkSecondary) else Modifier.background(Background))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            screen.icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    tint = if (active) PinkPrimary else TextPrimary,
                    contentDescription = "${screen.title} navigation icon",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }
            if (sideBarSize == 220.dp) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontFamily = fonts,
                    style = TextStyle(color = if (active) PinkPrimary else TextPrimary),
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}
