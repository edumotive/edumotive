package com.djinc.edumotive.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box() {
        Surface(
            shape = RectangleShape,
            color = Background,
            elevation = 12.dp,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Column() {
                    // LOGO
                    Image(
                        painter = painterResource(id = R.drawable.logo_edumotive),
                        contentDescription = "EduMotive logo",
                        modifier = Modifier
                            .padding(20.dp)
                            .width(180.dp)
                    )
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
                // WAVES
                Image(
                    painter = painterResource(id = R.drawable.wave),
                    contentDescription = "Waves",
                    modifier = Modifier
                        .width(220.dp)
                )
            }
        }
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxHeight()
                .width(220.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .offset(x = 18.dp)
                    .clip(CircleShape)
                    .background(
                        Background
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrows_right),
                    contentDescription = "Minimize sidebar",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .rotate(180f)
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
    val active = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    Box(
        contentAlignment = Alignment.CenterStart, modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
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
            Text(
                screen.title,
                fontSize = 16.sp,
                fontFamily = fonts,
                style = TextStyle(color = if (active) PinkPrimary else TextPrimary),
                modifier = Modifier.padding(top = 3.dp)
            )
        }
    }
}
