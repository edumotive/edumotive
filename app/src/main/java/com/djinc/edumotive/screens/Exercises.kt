package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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

@ExperimentalFoundationApi
@Composable
fun Exercises(nav: NavController, windowSize: WindowSize, viewModels: ViewModels) {
    Column(modifier = Modifier.padding(horizontal = if (windowSize == WindowSize.Compact) 20.dp else 40.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle(
            title = stringResource(R.string.exercises),
            windowSize = windowSize,
            viewModels = viewModels
        )
        LazySlider(
            direction = SliderDirection.Vertical,
            list = viewModels.exercises,
            component = SliderComponent.ExerciseCard,
            nav = nav,
            windowSize = windowSize,
            viewModels = viewModels
        )
    }
}
