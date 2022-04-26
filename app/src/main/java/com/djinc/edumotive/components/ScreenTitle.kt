package com.djinc.edumotive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.R
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.constants.WindowSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.ui.theme.*

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
    val isSearching = remember { mutableStateOf(false) }
    var searchValue by rememberSaveable { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = spacerHeight.dp)
            .fillMaxWidth()
    ) {
        // TITLE
        if (!isSearching.value) {
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.then(if (manualPadding) Modifier.padding(start = if (windowSize == WindowSize.Compact) 20.dp else 40.dp) else Modifier)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .then(if (buttonPadding) Modifier.padding(end = if (windowSize == WindowSize.Compact) 20.dp else 40.dp) else Modifier)
                .padding(bottom = 4.dp)
        ) {
            // LANGUAGE BUTTON
            if (languageButton) {
                CustomIconButton(
                    iconId = R.drawable.ic_language,
                    imageDescription = "Change language button"
                ) {
                    viewModels.isLanguageModalOpen = true
                }
            }
            // SEARCH BUTTON
            if (searchButton) {
                if (windowSize == WindowSize.Compact) {
                    MobileSearchButton(
                        iconId = R.drawable.ic_search,
                        imageDescription = "Search button",
                        isSearchingState = isSearching.value,
                        searchValue = searchValue,
                        searchClicked = {
                            isSearching.value = !isSearching.value
                        },
                        searchValueChanged = {
                            searchValue = it
                        }
                    )
                } else {
                    // TODO TABLET SEARCH
                }
            }
        }
    }
}

@Composable
fun CustomIconButton(iconId: Int, imageDescription: String, callback: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                callback.invoke()
            }
            .background(PinkSecondary)
            .padding(10.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            tint = PinkPrimary,
            contentDescription = imageDescription,
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
        )
    }
}

@Composable
fun MobileSearchButton(
    iconId: Int,
    imageDescription: String,
    isSearchingState: Boolean,
    searchClicked: () -> Unit,
    searchValue: String,
    searchValueChanged: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val maxTextFieldLength = configuration.screenWidthDp - 84
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = searchValue,
            onValueChange = {
                searchValueChanged.invoke(it)
            },
            textStyle = TextStyle(color = BluePrimary, fontFamily = fonts, fontSize = 16.sp),
            singleLine = true,
            maxLines = 1,
            cursorBrush = SolidColor(PinkPrimary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Box(Modifier.weight(1f)) {
                        if (searchValue.isEmpty()) Text(
                            text = "Motorblok, Bougie...",
                            color = BlueSecondary,
                            fontSize = 16.sp,
                            fontFamily = fonts
                        )
                        innerTextField()
                    }
                }
            },
            modifier = Modifier
                .width(if (isSearchingState) maxTextFieldLength.dp else 0.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(PinkSecondary)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .then(
                    if (isSearchingState) Modifier.clip(
                        RoundedCornerShape(
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) else Modifier.clip(
                        RoundedCornerShape(8.dp)
                    )
                )
                .clickable {
                    searchClicked.invoke()
                }
                .background(PinkSecondary)
                .width(45.dp)
                .height(45.dp)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                tint = PinkPrimary,
                contentDescription = imageDescription,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }
    }
}
