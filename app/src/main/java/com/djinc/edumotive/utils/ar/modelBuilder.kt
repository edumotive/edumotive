package com.djinc.edumotive.utils.ar

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
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
    title: String,
    description: String,
    callback: (ArModelNode) -> Unit,
): ArModelNode {

    val modelNode = ArModelNode()
    modelNode.loadModelAsync(context = context,
        coroutineScope = lifecycle,
        glbFileLocation = modelUrl,
        onLoaded = {
            if (isSingular) modelNode.centerModel(origin = Position(x = 0.0f, y = -1f, z = 0.0f))
            val text = if(description.isNotEmpty()) {
                Html.fromHtml("<b>$title</b><br /><br />$description", HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(modelName, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            createTextNode(
                context,
                text,
                modelNode,
                isSingular,
                callback
            )
        }
    )
    return modelNode
}

fun createTextNode(
    context: Context,
    text: Spanned,
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
        if(text.length > 50) {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
            maxWidth = 750
        }
        setPadding(50, 25, 50, 25)
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
