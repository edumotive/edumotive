package com.djinc.edumotive.utils.ar

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.math.Position

fun createModel(context: Context, lifecycle: LifecycleCoroutineScope, modelName: String, modelUrl: String): ArModelNode {

    val modelNode = ArModelNode()
    modelNode.loadModelAsync(context = context,
        coroutineScope = lifecycle,
        glbFileLocation = modelUrl,
        onLoaded = {
            createTextNode(context, modelName, modelNode)
        }
    )

    return modelNode
}

fun createTextNode(context: Context, text: String, modelNode: ArModelNode) {
    val textNode = ArModelNode()
    var textRendarable : Renderable

    val textView = TextView(context)

    textView.apply {
        setPadding(6)
        setText(text)
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
        setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large)
    }

    ViewRenderable.builder()
        .setView(context, textView)
        .build()
        .thenAccept { renderable: ViewRenderable ->
            textRendarable = renderable
            textNode.setModel(textRendarable)
        }

    textNode.position = Position(x = 0.0f, y = 1.0f, z = 0.0f)
    textNode.isVisible = false
    modelNode.addChild(textNode)

    modelNode.apply {
        onTouched = { _, _ ->
            toggleVisibility(textNode)}
    }
}

private fun toggleVisibility(textNode: ArModelNode) {
    textNode.isVisible = !textNode.isVisible
}


