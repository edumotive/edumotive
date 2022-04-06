package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection
import com.djinc.edumotive.models.ViewModels
import com.djinc.edumotive.utils.WindowSize

@ExperimentalFoundationApi
@Composable
fun Parts(nav: NavController, windowSize: WindowSize, viewModels: ViewModels) {
    Column(modifier = Modifier.padding(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle(title = "Onderdelen", windowSize = windowSize)

        LazySlider(
                direction = SliderDirection.Vertical,
                list = viewModels.modelGroups,
                list2 = viewModels.models,
                component = SliderComponent.PartCard,
                nav = nav,
                windowSize = windowSize,
                viewModels = viewModels
        )
    }
}
