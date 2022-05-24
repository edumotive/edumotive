package com.djinc.edumotive.constants

enum class WindowSize { Compact, Medium, Expanded }
enum class ContentfulContentModel(val stringValue: String) {
    MODEL("MODEL"),
    MODELGROUP("MODELGROUP"),
    EXERCISE("EXERCISE"),
    EXERCISEMANUAL("EXERCISEMANUAL"),
    EXERCISEASSEMBLE("EXERCISEASSEMBLE"),
    EXERCISERECOGNITION("EXERCISERECOGNITION")
}
enum class SliderDirection { Horizontal, Vertical }
enum class SliderComponent { PartCard, ExerciseCard }
