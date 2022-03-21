package com.djinc.edumotive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun Sidebar() {
    Box(modifier = Modifier
        .background(color = Color.Gray)
        .padding(16.dp)
        .fillMaxHeight()) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row() {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .background(Color.Black)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("EduMotive")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row() {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .background(Color.Black)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Dashboard")
            }
            Row() {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .background(Color.Black)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Onderdelen")
            }
            Row() {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .background(Color.Black)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Oefeningen")
            }
        }
    }
}
