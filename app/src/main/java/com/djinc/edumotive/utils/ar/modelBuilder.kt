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
    callback: (ArModelNode) -> Unit
): ArModelNode {

    val modelNode = ArModelNode()
    modelNode.loadModelAsync(context = context,
        coroutineScope = lifecycle,
        glbFileLocation = modelUrl,
        onLoaded = {
            createTextNode(context, modelName, modelNode, callback)
        }
    )
    return modelNode
}

fun createEmptyModel(
    context: Context,
    lifecycle: LifecycleCoroutineScope,
    modelUrl: String
): ArModelNode {

    val modelNode = ArModelNode()
    modelNode.loadModelAsync(
        context = context,
        coroutineScope = lifecycle,
        glbFileLocation = modelUrl
    )

    return modelNode
}

fun createTextNode(
    context: Context,
    text: String,
    modelNode: ArModelNode,
    callback: (ArModelNode) -> Unit
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

    val yModel =
        if(modelNode.model != null) (modelNode.model!!.collisionShape as Box).size.y
        else 0.5f

    val zModel =
        if(modelNode.model != null) (modelNode.model!!.collisionShape as Box).size.z
        else 0.0f

    textNode.position = Position(x = 0.0f, y = yModel + 0.5f, z = -zModel / 2)
    textNode.isVisible = false
}
