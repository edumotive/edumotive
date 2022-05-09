package com.djinc.edumotive.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun AsyncImage(imageUrl: String, imageName: String) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = Uri.parse(imageUrl))
            .apply(block = fun ImageRequest.Builder.() {}).build()
    )
    val painterState = painter.state
    Image(
        painter = painter,
        contentDescription = "Image of $imageName",
        modifier = Modifier.fillMaxSize(1f)
    )
    if (painterState is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator()
    }
}
