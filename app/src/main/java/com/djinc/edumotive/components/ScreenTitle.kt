package com.djinc.edumotive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.ViewModels
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
    viewModels: ViewModels,
    searchCallback: ((String) -> Unit)? = null
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
                        isSearchingState = isSearching.value,
                        searchValue = searchValue,
                        searchClicked = {
                            isSearching.value = !isSearching.value
                        },
                        searchValueChanged = {
                            searchValue = it
                            searchCallback?.invoke(it)
                        }
                    )
                } else {
                    TabletSearchBox(
                        searchValue = searchValue,
                        searchValueChanged = {
                            searchValue = it
                            if (searchCallback != null) {
                                searchCallback(it)
                            }
                        }
                    )
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
    isSearchingState: Boolean,
    searchClicked: () -> Unit,
    searchValue: String,
    searchValueChanged: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val maxTextFieldLength = configuration.screenWidthDp - 84
    val focusManager = LocalFocusManager.current

    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = searchValue,
            onValueChange = {
                searchValueChanged.invoke(it)
            },
            textStyle = TextStyle(color = BluePrimary, fontFamily = fonts, fontSize = 16.sp),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
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

@Composable
fun TabletSearchBox(
    searchValue: String,
    searchValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = Modifier
            .width(400.dp)
            .height(45.dp)
            .background(Background)
            .drawBehind {
                val strokeWidth = 2 * density
                val y = size.height - strokeWidth / 2

                drawLine(
                    PinkPrimary,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
        value = searchValue,
        onValueChange = {
            searchValueChanged.invoke(it)
        },
        singleLine = true,
        cursorBrush = SolidColor(PinkPrimary),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        textStyle = TextStyle(color = BluePrimary, fontFamily = fonts, fontSize = 16.sp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
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
    )
}
