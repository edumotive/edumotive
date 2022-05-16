package com.djinc.edumotive.utils.ar

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import com.djinc.edumotive.R
import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.math.Position

fun createModel(
    context: Context,
    lifecycle: LifecycleCoroutineScope,
    modelUrl: String,
    modelName: String,
    isSingular: Boolean,
    callback: (ArModelNode) -> Unit,
): ArModelNode {

    val modelNode = ArModelNode()
    modelNode.loadModelAsync(context = context,
        coroutineScope = lifecycle,
        glbFileLocation = modelUrl,
        onLoaded = {
            if (isSingular) modelNode.centerModel(origin = Position(x = 0.0f, y = -1f, z = 0.0f))
            createTextNode(context, modelName, modelNode, isSingular, callback)
        }
    )
    return modelNode
}

fun createTextNode(
    context: Context,
    text: String,
    modelNode: ArModelNode,
    isSingular: Boolean,
    callback: (ArModelNode) -> Unit,
) {
    val textNode = ArModelNode()
    var textRenderable: Renderable

    val textView = TextView(context)

    textView.apply {
        setText(text)
        setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large)
        setTextColor(ContextCompat.getColor(context, android.R.color.black))
        setBackgroundResource(R.drawable.rounded_corner) // Rounded Corner
        setPadding(100, 50, 100, 50)
    }

    ViewRenderable.builder()
        .setView(context, textView)
        .build()
        .thenAccept { renderable: ViewRenderable ->
            textRenderable = renderable
            textNode.setModel(textRenderable)
            modelNode.addChild(textNode)
            callback(modelNode)
        }

    val position =
        if (isSingular) {
            Position(
                x = modelNode.position.x,
                y = 0.8f,
                z = modelNode.position.z + 1.3f + (modelNode.collisionShape as Box).size.z / 2
            )
        } else {
            Position(
                x = modelNode.position.x,
                y = 1f,
                z = 0.0f,
            )
        }

    textNode.position = position
    textNode.isVisible = false
}
