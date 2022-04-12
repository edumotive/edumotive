package com.djinc.edumotive.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.djinc.edumotive.R
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.navigation.Screen
import com.djinc.edumotive.ui.theme.*
import com.djinc.edumotive.utils.changeLocale
import java.util.*

@Composable
fun SideBar(navController: NavHostController, viewModels: ViewModels) {
    val screens = listOf(
        Screen.Dashboard,
        Screen.Parts,
        Screen.Exercises
    )
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var isLanguageDropdownOpen by remember { mutableStateOf(false) }
    var isMinimized by remember { mutableStateOf(false) }
    val sideBarSize: Dp by animateDpAsState(if (!isMinimized) 220.dp else 60.dp)

    Box() {
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
        // SIDEBAR LANGUAGE BUTTON
        if (sideBarSize > 80.dp) {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(sideBarSize)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(bottom = 12.dp, start = 12.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (viewModels.isLanguageModalOpen) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable {
                                        changeLocale(context, Locale("en", "US"))
                                        viewModels.refreshAll()
                                        viewModels.currentLocale = "en-US"
                                        viewModels.isLanguageModalOpen = false
                                    }
                                    .background(if (viewModels.currentLocale == "en-US") PinkSecondary else Background)
                                    .border(
                                        0.2.dp,
                                        TextSecondary,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(6.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_american_flag),
                                    contentDescription = "Choose English as language",
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(21.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                )
                                Text(
                                    text = "English",
                                    color = BluePrimary,
                                    fontSize = 16.sp,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable {
                                        changeLocale(context, Locale("nl", "NL"))
                                        viewModels.refreshAll()
                                        viewModels.currentLocale = "nl-NL"
                                        viewModels.isLanguageModalOpen = false
                                    }
                                    .background(if (viewModels.currentLocale == "nl-NL") PinkSecondary else Background)
                                    .border(
                                        0.2.dp,
                                        TextSecondary,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(6.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_dutch_flag),
                                    contentDescription = "Choose Dutch as language",
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(21.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                )
                                Text(
                                    text = "Nederlands",
                                    color = BluePrimary,
                                    fontSize = 16.sp,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    viewModels.isLanguageModalOpen = !viewModels.isLanguageModalOpen
                                }
                                .clip(RoundedCornerShape(4.dp))
                                .background(Background)
                                .padding(6.dp)
                        ) {
                            Image(
                                painter = painterResource(id = if (viewModels.currentLocale == "nl-NL") R.drawable.ic_dutch_flag else R.drawable.ic_american_flag),
                                contentDescription = "Choose language",
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(21.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_dropdown),
                                contentDescription = "Choose language dropdown arrow",
                                modifier = Modifier
                                    .width(15.dp)
                                    .height(9.dp)
                                    .rotate(if (viewModels.isLanguageModalOpen) 180f else 0f)
                            )
                        }
                    }
                }
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
fun ColumnScope.AddItem(
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
