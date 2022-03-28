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
import androidx.navigation.NavController
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection
import com.djinc.edumotive.models.Part

@ExperimentalFoundationApi
@Composable
fun Parts(nav: NavController) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        ScreenTitle("Onderdelen")

        val parts = listOf(
                Part(id = "abcdef", name = "Onderdeel 1"),
                Part(id = "abcdefg", name = "Onderdeel 2"),
                Part(id = "abcdefgh", name = "Onderdeel 3"),
                Part(id = "abcdefghi", name = "Onderdeel 4"),
                Part(id = "abcdefghij", name = "Onderdeel 5"),
                Part(id = "abcdefghijk", name = "Onderdeel 6"),
                Part(id = "abcdefghijkl", name = "Onderdeel 7"),
                Part(id = "abcdefghijklm", name = "Onderdeel 8"),
        )
        LazySlider(
                direction = SliderDirection.Vertical,
                lastElementOnPage = true,
                list = parts,
                component = SliderComponent.PartCard,
                nav = nav
        )
    }
}
