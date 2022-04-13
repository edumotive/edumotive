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

fun createModel(context: Context, lifecycle: LifecycleCoroutineScope, modelUrl: String, modelName: String): ArModelNode {

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

fun createEmptyModel(context: Context, lifecycle: LifecycleCoroutineScope, modelUrl: String): ArModelNode {

    val modelNode = ArModelNode()
    modelNode.loadModelAsync(context = context,
        coroutineScope = lifecycle,
        glbFileLocation = modelUrl
    )

    return modelNode
}

fun createTextNode(context: Context, text: String, modelNode: ArModelNode) {
    val textNode = ArModelNode()
    var textRenderable : Renderable

    val textView = TextView(context)

    textView.apply {
        setPadding(6)
        setText(text)
        setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large)
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
    }

    ViewRenderable.builder()
        .setView(context, textView)
        .build()
        .thenAccept { renderable: ViewRenderable ->
            textRenderable = renderable
            textNode.setModel(textRenderable)
            modelNode.addChild(textNode)
        }

    textNode.position = Position(x = 0.0f, y = 1.0f, z = 0.0f)
    textNode.isVisible = false
}


