package com.djinc.edumotive.screens

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.djinc.edumotive.components.LazySlider
import com.djinc.edumotive.components.ScreenTitle
import com.djinc.edumotive.components.SliderComponent
import com.djinc.edumotive.components.SliderDirection
import com.djinc.edumotive.models.Part
import com.djinc.edumotive.screens.ar.ARActivity
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary

@ExperimentalFoundationApi
@Composable
fun PartDetails(partId: String = "", nav: NavController) {
    val context = LocalContext.current
    // GET DATA FROM PART BASED ON GIVEN ID

    Column(modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(1f)
        ) {
            ScreenTitle(title = "Onderdeel")
            Button(
                    onClick = {
                        context.startActivity(Intent(context, ARActivity::class.java))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = PinkSecondary),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Open in AR", color = PinkPrimary, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
                modifier = Modifier
                        .fillMaxWidth(1f)
                        .aspectRatio(1F)
                        .background(PinkSecondary, RoundedCornerShape(8.dp))
        ) {

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Informatie", style = MaterialTheme.typography.h4)
        Text(
                text = "Amet hendrerit amet, donec vulputate auctor imperdiet curabitur sagittis. Integer vitae id a, nunc, vestibulum consectetur nunc, cursus. Nibh vulputate vitae arcu sed ac eu. Massa ultricies sodales sagittis, consequat, egestas lorem sit.",
                style = MaterialTheme.typography.body2,
        )

        Spacer(modifier = Modifier.height(20.dp))

        ScreenTitle(title = "Bijbehorende onderdelen")

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
