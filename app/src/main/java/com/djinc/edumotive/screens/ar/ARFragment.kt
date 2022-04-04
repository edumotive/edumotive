package com.djinc.edumotive.screens.ar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.djinc.edumotive.R
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.utils.ar.createModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Anchor
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.CursorNode
import io.github.sceneview.math.Position
import io.github.sceneview.utils.doOnApplyWindowInsets

class ARFragment : Fragment(R.layout.fragment_ar) {
    private lateinit var sceneView: ArSceneView
    private lateinit var loadingView: View
    private lateinit var actionButton: ExtendedFloatingActionButton

    private lateinit var cursorNode: CursorNode

    private val arModels = mutableListOf<ArModelNode>()

    private var models: List<ContentfulModel> = listOf(
        ContentfulModel(
            "1",
            "Model",
            title = "Engine",
            "models/V8_motor.png",
            "THis is an engine",
            "models/V8_motor.glb"
        )
    )

    private var isLoading = false
        set(value) {
            field = value
            loadingView.isGone = !value
            actionButton.isGone = value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadingView = view.findViewById(R.id.loadingView)
        actionButton = view.findViewById<ExtendedFloatingActionButton>(R.id.actionButton).apply {
            val bottomMargin = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
            doOnApplyWindowInsets { systemBarsInsets ->
                (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                    systemBarsInsets.bottom + bottomMargin
            }
            setOnClickListener { cursorNode.createAnchor()?.let { anchorOrMove(it) } }
        }

        sceneView = view.findViewById<ArSceneView?>(R.id.sceneView).apply {
            planeRenderer.isVisible = false
            // Handle a fallback in case of non AR usage. The exception contains the failure reason
            // e.g. SecurityException in case of camera permission denied
            onArSessionFailed = { _: Exception ->
                // If AR is not available or the camara permission has been denied, we add the model
                // directly to the scene for a fallback 3D only usage
                arModels.forEach { arModel ->
                    arModel.centerModel(origin = Position(x = 0.0f, y = 0.0f, z = 0.0f))
                    arModel.scaleModel(units = 1.0f)
                    sceneView.addChild(arModel)
                }
            }
        }

        cursorNode = CursorNode(context = requireContext(), coroutineScope = lifecycleScope).apply {
            onTrackingChanged = { _, isTracking, _ ->
                isLoading = false
                if (!isLoading) {
                    actionButton.isGone = !isTracking
                    actionButton.text = getString(R.string.move_object)
                    actionButton.setIconResource(R.drawable.ic_target)
                }
            }
        }
        sceneView.addChild(cursorNode)

        isLoading = true

        models.forEach { model ->
            arModels.add(createModel(requireContext(), lifecycleScope, model.title, model.modelURL))
        }
    }

    private fun anchorOrMove(anchor: Anchor) {
        arModels.forEach { arModel ->
            if (!sceneView.children.contains(arModel)) {
                sceneView.addChild(arModel)
            }
            arModel.anchor = anchor
        }
    }
}
