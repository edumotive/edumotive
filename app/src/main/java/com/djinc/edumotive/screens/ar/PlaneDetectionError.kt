package com.djinc.edumotive.screens.ar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.fonts

@Composable
fun PlaneDetectionError() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp + 1
    val windowSize =
        if (screenWidth < 600) WindowSize.Compact else if (screenWidth < 840) WindowSize.Medium else WindowSize.Expanded

    Box(
        contentAlignment = if (windowSize == WindowSize.Expanded) Alignment.BottomStart else Alignment.TopEnd,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Icon(
                Icons.Rounded.Warning,
                contentDescription = "Warning icon",
                tint = PinkPrimary,
            )
            Text(
                text = stringResource(id = R.string.cannot_find_plane),
                color = PinkPrimary,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                fontFamily = fonts,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}