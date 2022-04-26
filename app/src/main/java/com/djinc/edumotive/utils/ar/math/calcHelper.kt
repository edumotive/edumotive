package com.djinc.edumotive.utils.ar.math

import io.github.sceneview.math.Position
import kotlin.math.atan2


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
