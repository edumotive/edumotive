package com.djinc.edumotive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection

@ExperimentalFoundationApi
@Composable
fun Parts() {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 32.dp)) {
        ScreenTitle("Onderdelen")

        val parts = listOf(
            Part(name = "Onderdeel 1"),
            Part(name = "Onderdeel 2"),
            Part(name = "Onderdeel 3"),
            Part(name = "Onderdeel 4"),
            Part(name = "Onderdeel 5"),
            Part(name = "Onderdeel 6"),
            Part(name = "Onderdeel 7"),
            Part(name = "Onderdeel 8"),
        )
        LazySlider(
            direction = SliderDirection.Vertical,
            list = parts,
            component = SliderComponent.PartCard
        )
    }
}