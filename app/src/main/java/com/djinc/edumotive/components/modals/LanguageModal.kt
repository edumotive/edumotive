package com.djinc.edumotive.components.modals

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djinc.edumotive.R
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkSecondary
import com.djinc.edumotive.ui.theme.TextPrimary
import com.djinc.edumotive.ui.theme.fonts
import com.djinc.edumotive.utils.changeLocale
import java.util.*

@Composable
fun LanguageModal(windowSize: WindowSize, viewModels: ViewModels) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = interactionSource, indication = null) { }
    ) {
        Column(
            modifier = Modifier
                .background(Background)
                .padding(top = 32.dp, bottom = 24.dp)
                .fillMaxWidth()
        ) {
            ScreenTitle(
                title = stringResource(R.string.choose_language),
                manualPadding = true,
                windowSize = windowSize,
                viewModels = viewModels
            )
            AddLanguageRow(
                locale = listOf("nl", "NL"),
                languageName = "Nederlands",
                contentDescription = stringResource(R.string.choose_language_nl),
                flagId = R.drawable.ic_dutch_flag,
                context = context,
                windowSize = windowSize,
                viewModels = viewModels
            )
            AddLanguageRow(
                locale = listOf("en", "US"),
                languageName = "English",
                contentDescription = stringResource(R.string.choose_language_en),
                flagId = R.drawable.ic_american_flag,
                context = context,
                windowSize = windowSize,
                viewModels = viewModels
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color(0x66000000))
                .clickable {
                    viewModels.isLanguageModalOpen = false
                }
        )
    }
}

@Composable
fun AddLanguageRow(
    locale: List<String>,
    languageName: String,
    contentDescription: String,
    flagId: Int,
    context: Context,
    windowSize: WindowSize,
    viewModels: ViewModels
) {
    val localeString = locale.joinToString(separator = "-")
    Box(
        modifier = Modifier
            .background(if (viewModels.currentLocale == localeString) PinkSecondary else Background)
            .fillMaxWidth()
            .clickable {
                changeLocale(context, Locale(locale[0], locale[1]))
                viewModels.currentLocale = localeString
                viewModels.refreshAll()
                viewModels.isLanguageModalOpen = false
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp,
                vertical = 12.dp
            )
        ) {
            Image(
                painter = painterResource(id = flagId),
                contentDescription = contentDescription,
                modifier = Modifier
                    .width(40.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(2.dp))
            )
            Text(
                text = languageName,
                color = TextPrimary,
                fontSize = 18.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
