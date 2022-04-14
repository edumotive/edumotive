package com.djinc.edumotive.screens.ar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.djinc.edumotive.R
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup
import com.djinc.edumotive.utils.ar.createEmptyModel
import com.djinc.edumotive.utils.ar.createModel
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
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

    private var models = mutableListOf<ContentfulModel>()
    private var tModel: ArModelNode? = null

    private var loadedModels = mutableStateOf(0)

    private var isLoading = true
        set(value) {
            field = value
            loadingView.isGone = !value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params = this.arguments

        if (params != null) {
            if (params.getString("type") == "model") {
                Contentful().fetchModelByID(
                    id = params.getString("id")!!,
                    errorCallBack = ::errorCatch
                ) { model: ContentfulModel ->
                    models.add(model)
                    loadModels()
                }
            } else {
                Contentful().fetchModelGroupById(
                    id = params.getString("id")!!,
                    errorCallBack = ::errorCatch
                ) { modelGroup: ContentfulModelGroup ->
                    models.addAll(modelGroup.models)
                    val tArModel = createEmptyModel(requireContext(), lifecycleScope, modelGroup.modelUrl)

                    tArModel.isVisible = false

                    tArModel.apply {
                        onTouched = { _, _ ->
                            tArModel.isVisible = false
                            arModels.forEach {
                                    model ->
                                if(!model.isVisible) model.isVisible = true
                                model.children.forEach { child -> child.isVisible = false}
                            }
                        }
                    }

                    tModel = tArModel
                    loadModels()
                }
            }
        }

        loadingView = view.findViewById(R.id.loadingView)
        actionButton = view.findViewById<ExtendedFloatingActionButton>(R.id.actionButton).apply {
            val bottomMargin = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
            doOnApplyWindowInsets { systemBarsInsets ->
                (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                    systemBarsInsets.bottom + bottomMargin
            }
            setOnClickListener { if(!isLoading) {cursorNode.createAnchor()?.let { anchorOrMove(it) } } }
            isGone = false
        }
        actionButton.text = getString(R.string.loading)

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

        cursorNode = CursorNode(context = requireContext(), coroutineScope = lifecycleScope)
        sceneView.addChild(cursorNode)

        isLoading = true
    }

    private fun loadModels() {
        models.forEach { model ->
            val arModel = createModel(requireContext(), lifecycleScope, model.modelUrl, model.title) {
                whenLoaded {
                    isLoading = false
                    actionButton.text = getString(R.string.move_object)
                    actionButton.setIconResource(R.drawable.ic_target)
                }
            }

            arModel.apply {
                onTouched = { _, _ ->
                    if (tModel != null) {
                        tModel!!.isVisible = true
                    }
                    arModels.forEach {
                            model ->
                        if(model != arModel) {
                            model.isVisible = !model.isVisible
                        } else {
                            model.children.forEach { child -> child.isVisible = !child.isVisible}
                        }
                    }
                }
            }

            arModels.add(arModel)
        }
    }

    private fun anchorOrMove(anchor: Anchor) {
        arModels.forEach { arModel ->
            if (!sceneView.children.contains(arModel)) {
                sceneView.addChild(arModel)
            }
            arModel.anchor = anchor
        }

        if(tModel != null) {
            if (!sceneView.children.contains(tModel!!)) {
                sceneView.addChild(tModel!!)
            }
            tModel!!.anchor = anchor
        }
    }

    @SuppressLint("SetTextI18n")
    private fun whenLoaded(callBack: () -> Unit) {
        loadedModels.value = loadedModels.value + 1
        actionButton.text = getString(R.string.loading_models) + " " + loadedModels.value + "/" + models.size

        if(loadedModels.value == models.size) {
            callBack()
        }
    }
}
