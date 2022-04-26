package com.djinc.edumotive.screens.ar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.djinc.edumotive.R
import com.djinc.edumotive.ui.theme.Background

@Composable
fun BackButton(callback: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Button(
            onClick = { callback.invoke() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Background),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Go back button",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        }
    }
}