package com.djinc.edumotive.utils.ar.math

import io.github.sceneview.math.Position
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt


fun calcRotationAngleInDegrees(centerPt: Position, targetPt: Position): Double {
    var theta = atan2((targetPt.z - centerPt.z).toDouble(),
        (targetPt.x - centerPt.x).toDouble()
    )
    theta += Math.PI / 2.0
    var angle = Math.toDegrees(theta)
    if (angle < 0) {
        angle += 360.0
    }
    return angle
}

fun calcDistance(p1: Position, p2: Position): Double{
    return sqrt(
        (p1.x - p2.x).toDouble().pow(2) +
        (p1.y - p2.y).toDouble().pow(2) +
        (p1.z - p2.z).toDouble().pow(2)
    )
}
