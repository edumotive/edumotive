package com.djinc.edumotive.utils.contentful

import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.image.ImageOption
import com.djinc.edumotive.constants.ContentfulContentModel
import com.djinc.edumotive.models.*


/// Model
fun ContentfulModel.Companion.fromRestEntry(
    entry: CDAEntry
): ContentfulModel = ContentfulModel(
    entry.id(),
    ContentfulContentModel.valueOf(entry.contentType().id().uppercase()),
    entry.getField<String?>("title").orEmpty(),
    try {
        entry.getField<CDAAsset?>("image")
            ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.webp))
            .orEmpty()
    } catch (_: Throwable) {
        ""
    },
    entry.getField("info"),
    try {
        "https:" +
                entry.getField<CDAAsset>("object")
                    ?.url()
                    .orEmpty()
    } catch (_: Throwable) {
        ""
    }
)

/// ModelGroup
fun ContentfulModelGroup.Companion.fromRestEntry(
    entry: CDAEntry
): ContentfulModelGroup = ContentfulModelGroup(
    entry.id(),
    ContentfulContentModel.valueOf(entry.contentType().id().uppercase()),
    entry.getField<String?>("title").orEmpty(),
    try {
        entry.getField<CDAAsset?>("image")
            ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.png))
            .orEmpty()
    } catch (_: Throwable) {
        ""
    },
    entry.getField<String?>("info").orEmpty(),
    entry.getField<List<CDAEntry>?>("models")
        .orEmpty()
        .map { ContentfulModel.fromRestEntry(it) }
        .toMutableList()
)

/// Exercise
fun ContentfulExercise.Companion.fromRestEntry(
    entry: CDAEntry
): ContentfulExercise = ContentfulExercise(
    entry.id(),
    entry.getField<String?>("title").orEmpty(),
    try {
        entry.getField<CDAAsset?>("image")
            ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.webp))
            .orEmpty()
    } catch (_: Throwable) {
        ""
    },
    entry.getField<String?>("info").orEmpty(),
    entry.getField<String?>("chapter").orEmpty(),
    entry.getField("minimalTime"),
    entry.getField("maximumTime"),
    entry.getField<List<String>?>("steps").orEmpty(),
    entry.getField<List<CDAEntry>?>("models")
        .orEmpty()
        .map { ContentfulModel.fromRestEntry(it) }
        .toMutableList()
)

fun ContentfulExerciseManual.Companion.fromRestEntry(
    entry: CDAEntry
): ContentfulExerciseManual = ContentfulExerciseManual(
    entry.id(),
    ContentfulContentModel.valueOf(entry.contentType().id().uppercase()),
    entry.getField<String?>("title").orEmpty(),
    try {
        entry.getField<CDAAsset?>("image")
            ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.webp))
            .orEmpty()
    } catch (_: Throwable) {
        ""
    },
    entry.getField<String?>("info").orEmpty(),
    entry.getField("minimalTime"),
    entry.getField("maximumTime"),
    entry.getField<List<CDAEntry>?>("steps")
        .orEmpty()
        .map { ContentfulModelStep.fromRestEntry(it) }
        .toMutableList()
)

fun ContentfulExerciseAssemble.Companion.fromRestEntry(
    entry: CDAEntry
): ContentfulExerciseAssemble = ContentfulExerciseAssemble(
    entry.id(),
    ContentfulContentModel.valueOf(entry.contentType().id().uppercase()),
    entry.getField<String?>("title").orEmpty(),
    try {
        entry.getField<CDAAsset?>("image")
            ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.webp))
            .orEmpty()
    } catch (_: Throwable) {
        ""
    },
    entry.getField<String?>("info").orEmpty(),
    entry.getField("minimalTime"),
    entry.getField("maximumTime"),
    entry.getField<List<CDAEntry>?>("steps")
        .orEmpty()
        .map { ContentfulModelStep.fromRestEntry(it) }
        .toMutableList()
)

fun ContentfulExerciseRecognition.Companion.fromRestEntry(
    entry: CDAEntry
): ContentfulExerciseRecognition = ContentfulExerciseRecognition(
    entry.id(),
    ContentfulContentModel.valueOf(entry.contentType().id().uppercase()),
    entry.getField<String?>("title").orEmpty(),
    try {
        entry.getField<CDAAsset?>("image")
            ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.webp))
            .orEmpty()
    } catch (_: Throwable) {
        ""
    },
    entry.getField<String?>("info").orEmpty(),
    entry.getField("minimalTime"),
    entry.getField("maximumTime"),
    entry.getField<List<CDAEntry>?>("steps")
        .orEmpty()
        .map { ContentfulModelStep.fromRestEntry(it) }
        .toMutableList()
)

fun ContentfulModelStep.Companion.fromRestEntry(
    entry: CDAEntry
): ContentfulModelStep = ContentfulModelStep(
    entry.id(),
    entry.getField<String?>("title").orEmpty(),
    if (entry.getField<CDAEntry?>("modelGroup") != null)
        ContentfulModelGroup.fromRestEntry(entry.getField("modelGroup"))
    else null,
    entry.getField<List<CDAEntry>?>("models")
        .orEmpty()
        .map { ContentfulModel.fromRestEntry(it) }
        .toMutableList(),
    entry.getField<String?>("stepInfo").orEmpty(),
    entry.getField("stepIndex"),
)
