package com.djinc.edumotive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.R
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary
import com.djinc.edumotive.ui.theme.TextSecondary
import com.djinc.edumotive.utils.WindowSize

@Composable
fun ScreenTitle(
    title: String = "",
    languageButton: Boolean = false,
    searchButton: Boolean = false,
    buttonPadding: Boolean = true,
    spacerHeight: Int = 12,
    manualPadding: Boolean = false,
    windowSize: WindowSize,
    viewModels: ViewModels
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = spacerHeight.dp)
            .fillMaxWidth()
    ) {
        // TITLE
        Text(
            text = title,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.then(if (manualPadding) Modifier.padding(start = if (windowSize == WindowSize.Compact) 20.dp else 40.dp) else Modifier)
        )
//        if (windowSize == WindowSize.Compact) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .then(if (buttonPadding) Modifier.padding(end = if (windowSize == WindowSize.Compact) 20.dp else 40.dp) else Modifier)
                .padding(bottom = 4.dp)
        ) {
            // LANGUAGE BUTTON
            if (languageButton) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            viewModels.isLanguageModalOpen = true
                        }
                        .background(PinkSecondary)
                        .padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_language),
                        tint = PinkPrimary,
                        contentDescription = "Change language button",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
            }
            if (searchButton) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
//                        TODO
                        }
                        .background(PinkSecondary)
                        .padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        tint = PinkPrimary,
                        contentDescription = "Search button",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
            }
        }
//        }
    }
}
