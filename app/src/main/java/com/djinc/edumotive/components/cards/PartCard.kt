package com.djinc.edumotive.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.djinc.edumotive.navigation.Screen
import com.djinc.edumotive.ui.theme.Background
import com.djinc.edumotive.ui.theme.PinkPrimary
import com.djinc.edumotive.ui.theme.PinkSecondary

@Composable
fun PartCard(partId: String, partName: String = "", nav: NavController) {
    Card(
            backgroundColor = Background,
            elevation = 3.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                    .width(200.dp)
                    .clickable {
                        nav.navigate("part/$partId")
                    }
    ) {
        Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(12.dp)
        ) {
            Box(
                    modifier = Modifier
                            .fillMaxWidth(1f)
                            .aspectRatio(1.0F)
                            .background(PinkPrimary)
            )
            Text(text = partName, style = MaterialTheme.typography.h3)
        }
    }
}
