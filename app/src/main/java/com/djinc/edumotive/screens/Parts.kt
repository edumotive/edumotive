package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.R
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.constants.SliderComponent
import com.djinc.edumotive.constants.SliderDirection
import com.djinc.edumotive.constants.WindowSize
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.utils.contentful.filterModelGroupList
import com.djinc.edumotive.utils.contentful.filterModelList

@ExperimentalFoundationApi
@Composable
fun Parts(nav: NavController, windowSize: WindowSize, viewModels: ViewModels) {
    var listModel by remember { mutableStateOf(viewModels.models) }
    var listModelGroup by remember { mutableStateOf(viewModels.modelGroups) }

    Column(modifier = Modifier.padding(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle(
            title = stringResource(R.string.parts),
            searchButton = true,
            buttonPadding = false,
            windowSize = windowSize,
            viewModels = viewModels
        ) { filter ->
            filterModelList(viewModels.models, filter) { listModel = it }
            filterModelGroupList(viewModels.modelGroups, filter) { listModelGroup = it }
        }

        LazySlider(
            direction = SliderDirection.Vertical,
            list = listModelGroup,
            list2 = listModel,
            component = SliderComponent.PartCard,
            nav = nav,
            windowSize = windowSize,
            viewModels = viewModels
        )
    }
}
