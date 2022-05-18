package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.MainEdumotive
import com.djinc.edumotive.R
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.constants.SliderComponent
import com.djinc.edumotive.constants.SliderDirection
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.ui.theme.fonts
import com.djinc.edumotive.utils.contentful.filterModelGroupList
import com.djinc.edumotive.utils.contentful.filterModelList

@ExperimentalFoundationApi
@Composable
fun Parts(nav: NavController, windowSize: WindowSize) {
    val filteredModelGroups = remember { mutableStateOf(MainEdumotive.modelGroups) }
    val filteredModels = remember { mutableStateOf(MainEdumotive.models) }

    Column(modifier = Modifier.padding(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle(
            title = stringResource(R.string.parts),
            searchButton = true,
            buttonPadding = false,
            windowSize = windowSize
        ) { filter ->
            filterModelList(MainEdumotive.models, filter.trim()) { filteredModels.value = it }
            filterModelGroupList(MainEdumotive.modelGroups, filter.trim()) {
                filteredModelGroups.value = it
            }
        }

        if (filteredModelGroups.value.isEmpty() && filteredModels.value.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_no_results),
                        contentDescription = "No results found",
                        alignment = Alignment.Center
                    )
                }
                Text(
                    text = stringResource(R.string.search_no_results),
                    fontFamily = fonts,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(if (windowSize == WindowSize.Compact) .8f else .4f)
                        .padding(top = 20.dp)
                )
            }
            LazySlider(
                direction = SliderDirection.Vertical,
                list = filteredModelGroups.value,
                list2 = filteredModels.value,
                component = SliderComponent.PartCard,
                nav = nav,
                windowSize = windowSize
            )
        } else {
            LazySlider(
                direction = SliderDirection.Vertical,
                list = filteredModelGroups.value,
                list2 = filteredModels.value,
                component = SliderComponent.PartCard,
                nav = nav,
                windowSize = windowSize
            )
        }
    }
}
