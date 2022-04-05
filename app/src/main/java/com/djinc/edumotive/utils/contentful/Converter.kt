package com.djinc.edumotive.utils.contentful

import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.image.ImageOption
import com.djinc.edumotive.models.ContentfulExercise
import com.djinc.edumotive.models.ContentfulModel
import com.djinc.edumotive.models.ContentfulModelGroup


/// Model
fun ContentfulModel.Companion.fromRestEntry(
        entry: CDAEntry
): ContentfulModel = ContentfulModel(
        entry.id(),
        entry.contentType().id(),
        entry.getField<String?>("title").orEmpty(),
        try {
            entry.getField<CDAAsset?>("image")
                    ?.urlForImageWith(ImageOption.https(), ImageOption.formatOf(ImageOption.Format.webp))
                    .orEmpty()
        } catch (_: Throwable) {
            ""
        },
        entry.getField<String?>("info"),
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
        entry.contentType().id(),
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
                .map { ContentfulModel.fromRestEntry(it) },
        try {
                "https:" +
                        entry.getField<CDAAsset>("object")
                        ?.url()
                        .orEmpty()
        } catch (_: Throwable) {
                ""
        }
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
        entry.getField<Int>("minimalTime"),
        entry.getField<Int>("maximumTime"),
        entry.getField<List<String>?>("steps").orEmpty(),
        entry.getField<List<CDAEntry>?>("models")
                .orEmpty()
                .map { ContentfulModel.fromRestEntry(it) }
)
