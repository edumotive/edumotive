package com.djinc.edumotive.screens.ar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.djinc.edumotive.R
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.models.*
import com.djinc.edumotive.utils.LoadHelper
import com.djinc.edumotive.utils.ar.createModel
import com.djinc.edumotive.utils.ar.math.calcDistance
import com.djinc.edumotive.utils.ar.math.calcRotationAngleInDegrees
import com.djinc.edumotive.utils.ar.math.countModelsInSteps
import com.djinc.edumotive.utils.contentful.Contentful
import com.djinc.edumotive.utils.contentful.errorCatch
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Anchor
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.CursorNode
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.utils.doOnApplyWindowInsets


class ARFragment : Fragment(R.layout.fragment_ar) {
    /// Views
    private lateinit var sceneView: ArSceneView
    private lateinit var loadingView: View
    private lateinit var actionButton: ExtendedFloatingActionButton
    private lateinit var planeSelectorTextView: ComposeView
    private lateinit var drawerView: ComposeView
    private lateinit var backButton: ComposeView

    /// Anchor Node
    private lateinit var cursorNode: CursorNode

    /// AR Model data
    private var models = mutableListOf<ContentfulModel>()
    private var steps = mutableListOf<ContentfulModelStep>()
    private var selectedModelIndex = mutableStateOf(0)
    private var isModelSelected = mutableStateOf(false)

    /// Exercises
    private lateinit var currentType: String
    private lateinit var currentId: String
    private var currentStep = mutableStateOf(0)
    private var falseAnswersRecognition = mutableStateOf(0)


    private var isLoading = true
        set(value) {
            field = value
            loadingView.isGone = !value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val params = this.arguments
        if(params != null) {
            currentType = params.getString("type")!!
            currentId = params.getString("id")!!
        }

        backButton = view.findViewById(R.id.backButton)
        drawerView = view.findViewById(R.id.partDrawer)
        loadingView = view.findViewById(R.id.loadingView)
        planeSelectorTextView = view.findViewById<ComposeView>(R.id.planeSelectorTextView).apply {
            isGone = false
        }
        actionButton = view.findViewById<ExtendedFloatingActionButton>(R.id.actionButton).apply {
            val bottomMargin = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
            doOnApplyWindowInsets { systemBarsInsets ->
                (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                    systemBarsInsets.bottom + bottomMargin
            }
            setOnClickListener {
                if (!isLoading) cursorNode.createAnchor()?.let {
                    if (params != null) {
                        anchorOrMove(it)
                    }
                }
            }
            isGone = false
        }
        actionButton.text = getString(R.string.loading)
        planeSelectorTextView.setContent {
            PlaneDetectionError()
        }

        sceneView = view.findViewById<ArSceneView?>(R.id.sceneView).apply {
            planeRenderer.isVisible = false
            // Handle a fallback in case of non AR usage. The exception contains the failure reason
            // e.g. SecurityException in case of camera permission denied
            onArSessionFailed = { _: Exception ->
//                // If AR is not available or the camara permission has been denied, we add the model
//                // directly to the scene for a fallback 3D only usage
//                models.forEach { model ->
//                    model.arModel!!.centerModel(origin = Position(x = 0.0f, y = 0.0f, z = 0.0f))
//                    model.arModel!!.scaleModel(units = 1.0f)
//                    sceneView.addChild(model.arModel!!)
//                }
            }

            onFrame = { _ ->
                transformCard()
            }

            onTouchAr = { _, _ ->
                if (!isLoading) cursorNode.createAnchor()?.let {
                    if (params != null) {
                        anchorOrMove(it)
                    }
                }
            }

            onArFrame = { arFrame ->
                if (arFrame.session.planeFindingEnabled && arFrame.session.hasTrackedPlane && !planeSelectorTextView.isGone) {
                    planeSelectorTextView.isGone = true
                }

                if (arFrame.session.planeFindingEnabled && !arFrame.session.hasTrackedPlane) {
                    sceneView.instructions.searchPlaneInfoNode.textView?.setTextColor(
                        resources.getColor(
                            io.github.sceneview.R.color.mtrl_btn_transparent_bg_color, context.theme
                        )
                    )
                }
            }

            instructions.searchPlaneInfoNode.onViewLoaded = { _, viewScene ->
                viewScene.setBackgroundResource(io.github.sceneview.R.color.mtrl_btn_transparent_bg_color)

                fetchContentful()
            }
        }

        cursorNode = CursorNode(context = requireContext(), coroutineScope = lifecycleScope)
        sceneView.addChild(cursorNode)

        isLoading = true

        backButton.setContent {
            BackButton() {
                activity?.finish()
            }
        }
    }

    private fun transformCard() {
        if (isModelSelected.value) {
            models[selectedModelIndex.value].arModel!!.children.forEach { child ->
                val cameraPosition = sceneView.camera.worldPosition
                val cardPosition = child.worldPosition

                // Rotate card
                val angle = calcRotationAngleInDegrees(cameraPosition, cardPosition).toFloat()
                child.rotation = Rotation(
                    0.0f,
                    -angle + models[selectedModelIndex.value].arModel!!.worldRotation.y,
                    0.0f
                )

                // Scale card
                val distance = calcDistance(cameraPosition, cardPosition).toFloat()
                child.scale = Scale(distance / 3)
            }
        }
    }

    private fun fetchContentful() {
        when (currentType) {
            ContentfulContentModel.MODEL.stringValue -> {
                Contentful().fetchModelByID(
                    id = currentId,
                    errorCallBack = ::errorCatch
                ) { model: ContentfulModel ->
                    models.add(model)
                    loadModels()
                }
            }
            ContentfulContentModel.MODELGROUP.stringValue -> {
                Contentful().fetchModelGroupById(
                    id = currentId,
                    errorCallBack = ::errorCatch
                ) { modelGroup: ContentfulModelGroup ->
                    models.addAll(modelGroup.models)
                    loadModels()
                }
            }
            ContentfulContentModel.EXERCISEMANUAL.stringValue -> {
                Contentful().fetchExercisesManualById(
                    id = currentId,
                    errorCallBack = ::errorCatch
                ) { exercisesManual: ContentfulExerciseManual ->
                    steps.addAll(exercisesManual.steps)
                    loadExerciseModels(ContentfulContentModel.EXERCISEMANUAL)
                }
            }
            ContentfulContentModel.EXERCISEASSEMBLE.stringValue -> {
                Contentful().fetchExercisesAssembleById(
                    id = currentId,
                    errorCallBack = ::errorCatch
                ) { exerciseAssemble: ContentfulExerciseAssemble ->
                    steps.addAll(exerciseAssemble.steps)
                    loadExerciseModels(ContentfulContentModel.EXERCISEASSEMBLE)
                }
            }
            ContentfulContentModel.EXERCISERECOGNITION.stringValue -> {
                Contentful().fetchExercisesRecognitionById(
                    id = currentId,
                    errorCallBack = ::errorCatch
                ) { exerciseRecognition: ContentfulExerciseRecognition ->
                    steps.addAll(exerciseRecognition.steps)
                    loadExerciseModels(ContentfulContentModel.EXERCISERECOGNITION)
                }
            }
        }
    }


    private fun loadModels() {
        val loadHelper = LoadHelper(amountNeeded = models.size)

        models.forEachIndexed { index, model ->
            if (models[index].arModel != null) {
                loadedModel(loadHelper)
            } else {
                createAndLoadModel(
                    model,
                    (models.size <= 1)
                ) {
                    models[index].arModel = addOnTouched(it)

                    loadedModel(loadHelper)
                }
            }
        }
    }

    private fun loadExerciseModels(type: ContentfulContentModel) {
        val loadHelper = LoadHelper(amountNeeded = countModelsInSteps(steps))

        steps.forEach { step ->
            if (step.hasModel()) {
                val isSingular = step.models.size == 1 && type != ContentfulContentModel.EXERCISEASSEMBLE
                step.models.forEach { model ->
                    createExerciseModel(
                        model = model,
                        isSingular = isSingular,
                        loadHelper = loadHelper,
                        type = type
                    )
                }
            } else if (step.hasModelGroup()) {
                step.modelGroup!!.models.forEach { model ->
                    createExerciseModel(
                        model = model,
                        isSingular = false,
                        loadHelper = loadHelper,
                        type = type
                    )
                }
            }
        }
    }

    private fun createExerciseModel(
        model: ContentfulModel,
        isSingular: Boolean,
        loadHelper: LoadHelper,
        type: ContentfulContentModel
    ) {
        if (model.arModel != null) {
            models.add(model)
            model.arModel!!.isVisible = false
            loadedModel(loadHelper) { startExercise(type) }
        } else {
            createAndLoadModel(
                model,
                isSingular
            ) {
                model.arModel = it
                models.add(model)
                model.arModel!!.isVisible = false
                loadedModel(loadHelper) { startExercise(type) }
            }
        }
    }

    private fun startExercise(type: ContentfulContentModel) {
        when (type) {
            ContentfulContentModel.EXERCISERECOGNITION ->
                startExerciseRecognition()
            ContentfulContentModel.EXERCISEMANUAL ->
                startExerciseManual()
            ContentfulContentModel.EXERCISEASSEMBLE ->
                startExerciseAssemble()
            else -> return
        }
    }

    private fun startExerciseRecognition() {
        steps.shuffle()
        showStep(currentStep.value)
    }

    private fun startExerciseManual() {

    }

    private fun startExerciseAssemble() {
        showStep(currentStep.value)
    }

    private fun nextStep(finishCallback: () -> Unit = {}) {
        if (currentStep.value < steps.size - 1) {
            currentStep.value = currentStep.value + 1
            showStep(currentStep.value)
        } else {
            finishCallback()
        }
    }

    private fun showStep(currentIndex: Int) {
        when (currentType) {
            ContentfulContentModel.EXERCISERECOGNITION.stringValue -> {
                steps.forEachIndexed { index, contentfulModelStep ->
                    if (index == currentIndex) {
                        contentfulModelStep.setVisibility(true)
                    } else {
                        contentfulModelStep.setVisibility(false)
                    }
                }
            }
            ContentfulContentModel.EXERCISEASSEMBLE.stringValue -> {
                steps[currentIndex].setVisibility(true)
            }
        }
    }

    private fun createAndLoadModel(
        model: ContentfulModel,
        isSingular: Boolean,
        doneLoading: (ArModelNode) -> Unit
    ) {
        createModel(
            requireContext(),
            lifecycleScope,
            model.modelUrl,
            model.title,
            isSingular
        ) {
            doneLoading(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadedModel(loadHelper: LoadHelper, callback: () -> Unit = {}) {
        loadHelper.whenLoaded(updateLoading = { amount ->
            actionButton.text =
                getString(R.string.loading_models) + " " + amount + "/" + loadHelper.maxAmount.value
        }) {
            isLoading = false
            actionButton.text = getString(R.string.move_object)
            actionButton.setIconResource(R.drawable.ic_target)
            val exerciseType = this.requireArguments().getString("type")
            val shuffledSteps = steps.toMutableList()
            shuffledSteps.shuffle()
            drawerView.setContent {
                PartDrawer(
                    list = models,
                    type = exerciseType,
                    steps = steps,
                    shuffledSteps = shuffledSteps,
                    currentStep = currentStep,
                    callback = { modelNode ->
                        selectModelVisibility(modelNode)
                    },
                    answerCallback = {
                        if (it) nextStep() {
                          /// Show finish modal
                        } else falseAnswersRecognition.value = falseAnswersRecognition.value + 1
                    }
                )
            }
            callback()
        }
    }

    private fun addOnTouched(arModel: ArModelNode): ArModelNode {
        arModel.apply {
            onTouchEvent = { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    selectModelVisibility(arModel)
                }
                true
            }
        }
        return arModel
    }

    private fun anchorOrMove(anchor: Anchor) {
        models.forEach { model ->
            if (!sceneView.children.contains(model.arModel!!)) sceneView.addChild(model.arModel!!)
            model.arModel!!.anchor = anchor
        }
    }

    private fun selectModelVisibility(arModel: ArModelNode) {
        // Select model to turn on
        // Select same model to turn off OR Select other model

        if (!isModelSelected.value) {
            selectedModelIndex.value = models.indexOfFirst { it.arModel == arModel }

            isModelSelected.value = true

            models.forEach { model ->
                if (model.arModel != arModel) model.arModel!!.isVisible = false
                else model.arModel!!.children.forEach { child -> child.isVisible = true }
            }
        } else {
            if (models.indexOfFirst { it.arModel == arModel } != selectedModelIndex.value) {
                // Select other model
                selectedModelIndex.value = models.indexOfFirst { it.arModel == arModel }

                models.forEach { model ->
                    if (model.arModel != arModel) {
                        model.arModel!!.isVisible = false
                        model.arModel!!.children.forEach { child -> child.isVisible = false }
                    } else {
                        model.arModel!!.isVisible = true
                        model.arModel!!.children.forEach { child -> child.isVisible = true }
                    }
                }
            } else {
                // Select same model
                isModelSelected.value = false

                models.forEach { model ->
                    if (model.arModel != arModel) model.arModel!!.isVisible = true
                    else model.arModel!!.children.forEach { child -> child.isVisible = false }
                }
            }
        }
    }
}
